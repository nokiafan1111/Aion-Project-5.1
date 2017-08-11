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
package ai.instance.cradleOfEternity;

import com.aionemu.gameserver.ai2.*;
import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.*;
import com.aionemu.gameserver.world.knownlist.Visitor;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Garden_Temple_Bind_Point")
public class Garden_Temple_Bind_PointAI2 extends NpcAI2
{
	@Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(this, creature);
    }
	
	@Override
	protected void handleCreatureMoved(Creature creature) {
		checkDistance(this, creature);
	}
	
	private void checkDistance(NpcAI2 ai, Creature creature) {
		if (creature instanceof Player && !creature.getLifeStats().isAlreadyDead()) {
        	if (MathUtil.isIn3dRange(getOwner(), creature, 5)) {
        		GardenTempleBindPoint();
        	}
        }
    }
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		announceGardenTemple();
	}
	
	private void GardenTempleBindPoint() {
		AI2Actions.deleteOwner(Garden_Temple_Bind_PointAI2.this);
		spawn(281446, 713.17285f, 1191.1569f, 1036.5265f, (byte) 0);
		spawn(834043, 713.17285f, 1191.1569f, 1036.5265f, (byte) 0); //Geodesic <To The Library Gap>
    }
	
	private void announceGardenTemple() {
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (player.isOnline()) {
					//The Garden Temple's bind point device was activated.
					PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_IDEternity_02_SYSTEM_MSG_12);
				}
			}
		});
	}
	
	@Override
	public boolean isMoveSupported() {
		return false;
	}
}