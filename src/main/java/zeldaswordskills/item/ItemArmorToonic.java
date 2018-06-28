package zeldaswordskills.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorToonic extends ItemArmorTunic {

	public ItemArmorToonic(int renderIndex, int type) {
		super(renderIndex, type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
		Item item = stack.getItem();
		
		if (stack != null && item instanceof ItemArmorToonic) {
			int type = ((ItemArmor)item).armorType;
			// Armor model of scale 0 is the same size as the plauer. 1.0 is standard armor size
			ModelBiped armorModel;
			if (type == 2) {
				armorModel = new ModelBiped(0.2f);
			}
			else {
				armorModel = new ModelBiped(0.4f);
			}
			// Render model parts according to worn armor and held items
			armorModel.bipedHead.showModel = armorSlot == 0;
			armorModel.bipedHeadwear.showModel = armorSlot == 0;
			armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
			armorModel.bipedRightArm.showModel = armorSlot == 1;
			armorModel.bipedLeftArm.showModel = armorSlot == 1;
			armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
			armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;
			armorModel.isSneak = entityLiving.isSneaking();
			armorModel.isRiding = entityLiving.isRiding();
			armorModel.isChild = entityLiving.isChild();
			armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 :0;
			if (entityLiving instanceof EntityPlayer) {
				armorModel.aimedBow = ((EntityPlayer)entityLiving).getItemInUseDuration() > 2;
			}
			return armorModel;
		}
		return null;
	}
}
