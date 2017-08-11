/*
 * This file is part of Encom. **ENCOM FUCK OTHER SVN**
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package ai.worlds.iluma;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.*;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("forest_of_life_brohum_changeling")
public class Forest_Of_Life_Brohum_ChangelingAI2 extends AggressiveNpcAI2
{
	@Override
	protected void handleDied() {
		spawn(242883, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading()); //Young Forest Of Life Brohum.
		super.handleDied();
		AI2Actions.deleteOwner(this);
	}
}