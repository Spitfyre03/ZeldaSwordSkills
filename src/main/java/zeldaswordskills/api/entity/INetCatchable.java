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

package zeldaswordskills.api.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Interface for entities that can be caught by items implementing the IItemNet
 * interface.
 */
public interface INetCatchable {

	/**
	 * This method determines what ItemStack is to be returned as a result of
	 * being captured. Null indicates that the entity was not captured.
	 *
	 * @param stack
	 *        the stack used to capture this Entity
	 * 
	 * @param player
	 *        the player capturing this Entity
	 *
	 * @return the ItemStack representing the captured Entity. Null if it wasn't
	 *         captured
	 */
	public ItemStack getCapturedEntity(ItemStack stack, EntityPlayer player);

	/**
	 * Returns a stack indicating an item required for capturing the entity. The
	 * stack is size-sensitive, and null indicates that no item is required. If
	 * the player does not have enough of the item, the entity is not captured,
	 * and no items are subtracted from the inventory.
	 * 
	 * @return what stack is required for containing the Entity. This item is
	 *         consumed if the Entity is captured. Null if none required.
	 */
	public ItemStack getCapturingContainer(ItemStack stack, EntityPlayer player);
}
