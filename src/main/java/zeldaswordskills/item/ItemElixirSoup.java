/**
    Copyright (C) <2018> <coolAlias>

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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import zeldaswordskills.creativetab.ZSSCreativeTabs;

public class ItemElixirSoup extends BaseModItemFood {

	public ItemElixirSoup() {
		super(16, 5.0F, false);
		this.setMaxStackSize(1);
		setCreativeTab(ZSSCreativeTabs.tabTools);
		this.setPotionEffect(Potion.damageBoost.id, 1, 600, 1.0F);// Maybe should last longer than 1 sec.
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        super.onItemUseFinish(stack, worldIn, playerIn);
        if (this == ZSSItems.soupElixirFull) {
        	return new ItemStack(ZSSItems.soupElixirHalf);
        }
        else if (this == ZSSItems.soupElixirHalf) {
        	return new ItemStack(Items.glass_bottle);
        }
        return stack;
    }

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }
}
