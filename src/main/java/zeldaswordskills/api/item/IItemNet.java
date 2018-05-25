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

package zeldaswordskills.api.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Interface for any item that can be used to capture entities that implement
 * the INetCatchable interface. Items that implement this interface trigger the
 * capture phase when the player attacks an entity while holding this item.
 */
public interface IItemNet {

	/**
	 * Enum indicating the strength of a net
	 */
	public static enum NetStrength {
		WEAK, NORMAL, STRONG;
	}

	/**
	 * @return true for further damage processing
	 */
	public boolean onCapturedEntity(ItemStack stack, EntityPlayer player, Entity target);

	public NetStrength getNetStrength();
}
