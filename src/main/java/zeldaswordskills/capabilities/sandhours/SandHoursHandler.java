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

package zeldaswordskills.capabilities.sandhours;

import zeldaswordskills.api.capabilities.sandhours.ISandHoursHandler;

public class SandHoursHandler implements ISandHoursHandler {

	private int sands;
	private int maxSands = 100;

	public int getCurrentSands() {
		return sands;
	}

	public void setCurrentSands(int amount) {
		sands = Math.min(maxSands, Math.max(amount, 0));
	}

	/**
	 * @return a positive number representing the amount leftover from the argument that could
	 *         not be added to the handler, or 0 if the entire amount was added
	 */
	public int addAmount(int amount) {
		int leftover = Math.max((sands + amount) - maxSands, 0);
		setCurrentSands(sands + amount);
		return leftover;
	}


	/**
	 * @return a positive number representing the amount not removed from the holder's current, or
	 *         0 if the entire amount was removed
	 */
	public int removeAmount(int amount) {
		int defecit = Math.max(amount - sands, 0);
		setCurrentSands(sands - amount);
		return defecit;
	}

	public int getMaxSands() {
		return maxSands;
	}

	public void setMaxSands(int amount) {
		maxSands = Math.max(0, amount);
	}

}
