/**
    Copyright (C) <2014> <coolAlias>

    This file is part of coolAlias' Zelda Sword Skills Minecraft Mod; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package zeldaswordskills.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import zeldaswordskills.api.item.ArmorIndex;
import zeldaswordskills.creativetab.ZSSCreativeTabs;
import zeldaswordskills.entity.ZSSEntityInfo;
import zeldaswordskills.entity.buff.Buff;
import zeldaswordskills.lib.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * Link's various tunics. Despite being made out of home-spun cloth, they all provide protection
 * equivalent to chain mail. They have the same enchantibility as cloth, as well as using cloth
 * for anvil repairs.
 * 
 * Only the Hero's clothes include a full set; the other two are tunics only.
 * 
 * Hero's Tunic: standard green clothes worn by Link
 * Goron Tunic: special red tunic made from the scales of lava-dwelling Dodongos;
 * 		prevents all fire damage
 * Zora Tunic: special blue tunic allows wearer to breathe under water
 *
 */
public class ItemArmorTunic extends ItemArmor
{
	/** Effect to add every 50 ticks */
	protected PotionEffect tickingEffect = null;
	
	/**
	 * Armor types as used on player: 0 boots, 1 legs, 2 chest, 3 helm
	 * Armor types as used in armor class: 0 helm, 1 chest, 2 legs, 3 boots
	 */
	public ItemArmorTunic(int id, int renderIndex, int type) {
		super(id, EnumArmorMaterial.CHAIN, renderIndex, type);
		setCreativeTab(ZSSCreativeTabs.tabCombat);
	}
	
	/**
	 * Sets this piece of armor to grant the specified potion effect every 50 ticks
	 */
	public ItemArmorTunic setEffect(PotionEffect effect) {
		tickingEffect = effect;
		return this;
	}
	
	/**
	 * Returns this armor's ticking potion effect or null if it doesn't have one
	 */
	public PotionEffect getEffect() {
		return tickingEffect;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return true;
	}
	
	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack stack) {
		PotionEffect effect = getEffect();
		if (effect != null && shouldDamageArmor(world, player, stack, effect.getPotionID())) {
			player.addPotionEffect(new PotionEffect(effect));
			player.setAir(300);
			damageStack(stack, player, 1);
		}
	}
	
	/**
	 * Call from LivingAttackEvent when entity attacked by fire damage
	 * Checks if wearing Goron Tunic and negates damage if appropriate
	 * @return true if attack event should be canceled
	 */
	public static boolean onFireDamage(EntityLivingBase entity, float damage) {
		ItemStack stack = entity.getCurrentItemOrArmor(ArmorIndex.EQUIPPED_CHEST);
		if (!entity.worldObj.isRemote && stack != null && stack.getItem() == ZSSItems.tunicGoronChest) {
			PotionEffect resist = entity.getActivePotionEffect(Potion.fireResistance);
			if (resist != null && resist.duration > 0) {
				return false;
			}
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			if (!stack.getTagCompound().hasKey("lastDamaged") || entity.worldObj.getTotalWorldTime() > (stack.getTagCompound().getLong("lastDamaged") + 20)) {
				stack.getTagCompound().setLong("lastDamaged", entity.worldObj.getTotalWorldTime());
				damage *= 1.0F + (ZSSEntityInfo.get(entity).getBuffAmplifier(Buff.WEAKNESS_FIRE) * 0.01F);
				damage *= 1.0F - (ZSSEntityInfo.get(entity).getBuffAmplifier(Buff.RESIST_FIRE) * 0.01F);
				if (damage > 0) {
					((ItemArmorTunic) stack.getItem()).damageStack(stack, entity, Math.max((int) damage / 4, 1));
				}
				entity.extinguish();
			}
			
			return true;
		}
		return false;
	}
	
	/**
	 * Damages stack for amount, destroying if applicable
	 */
	private void damageStack(ItemStack stack, EntityLivingBase entity, int amount) {
		stack.damageItem(amount, entity);
		if (stack.stackSize == 0 || stack.getItemDamage() >= stack.getMaxDamage()) {
			entity.worldObj.playSoundAtEntity(entity, "random.break", 1.0F, 1.0F);
			entity.setCurrentItemOrArmor(EntityLiving.getArmorPosition(stack), null);
		}
	}
	
	/**
	 * Returns true if the armor should be damaged this tick for applying potion effect
	 */
	private boolean shouldDamageArmor(World world, EntityPlayer player, ItemStack stack, int effectID) {
		Material m = world.getBlockMaterial((int) player.posX, (int) player.posY + 1, (int) player.posZ);
		if (effectID == Potion.waterBreathing.id) {
			return (m == Material.water && world.getWorldTime() % 50 == 0);
		} else {
			return false;
		}
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		String name = getUnlocalizedName().substring(9, getUnlocalizedName().lastIndexOf("_"));
		if (slot == 2) {
			return ModInfo.ID + ":textures/armor/" + name + "_layer_2.png";
		} else {
			return ModInfo.ID + ":textures/armor/" + name + "_layer_1.png";
		}
	}
	
	@Override
	public int getItemEnchantability() {
		return EnumArmorMaterial.CLOTH.getEnchantability();
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack stack) {
		if (toRepair.getItem() == ZSSItems.tunicGoronChest) {
			// TODO return dodongo scales
			return stack.getItem() == Item.magmaCream;
		} else if (toRepair.getItem() == ZSSItems.tunicZoraChest) {
			// TODO return something interesting?
		}
		return toRepair.getItem() instanceof ItemArmorTunic && stack.itemID == Block.cloth.blockID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon(ModInfo.ID + ":" + getUnlocalizedName().substring(9));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack,	EntityPlayer player, List list, boolean par4) {
		if (stack.getItem() == ZSSItems.tunicHeroChest) {
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.kokiri.desc.0"));
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.kokiri.desc.1"));
		} else if (stack.getItem() == ZSSItems.tunicGoronChest) {
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.goron.desc.0"));
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.goron.desc.1"));
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.goron.desc.2"));
		} else if (stack.getItem() == ZSSItems.tunicZoraChest) {
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.zora.desc.0"));
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.zora.desc.1"));
		} else {
			list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.zss.tunic.generic.desc.0"));
		}
	}
}