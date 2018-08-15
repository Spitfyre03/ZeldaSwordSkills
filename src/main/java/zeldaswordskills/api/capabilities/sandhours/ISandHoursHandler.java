/**
    Copyright (C) <2019> <coolAlias>
    
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

package zeldaswordskills.api.capabilities.sandhours;

/**
 * A capability used to hook a counter to items that provide protection against Phantom
 * Blocks and the like.
 *
 * @author Spitfyre03
 */
public interface ISandHoursHandler {

	/**
	 * @return The current amount the holder contains
	 */
	int getCurrentSands();

	/**
	 * @param amount
	 *        The amount to set the holder's current value to
	 */
	void setCurrentSands(int amount);

	/**
	 * @param amount
	 *        The amount to be added to the holder. Should cap to the holder's maximum
	 *
	 * @return The remaining amount that could not be added to the holder
	 */
	int addAmount(int amount);

	/**
	 * @param amount
	 *        The amount to be removed from the holder
	 *
	 * @return The remaining amount that could not be removed from the holder
	 */
	int removeAmount(int amount);

	/**
	 * @return The maximum value the holder can contain
	 */
	int getMaxSands();

	/**
	 * @param amount
	 *        The value to set the maximum to
	 */
	void setMaxSands(int amount);
}
