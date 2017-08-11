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
package ai.rvr.elyosWarshipInvasion;

import ai.AggressiveNpcAI2;

import java.util.concurrent.atomic.AtomicBoolean;

import com.aionemu.gameserver.ai2.*;
import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.services.*;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.*;
import com.aionemu.gameserver.world.knownlist.Visitor;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("luluran_comander")
public class Luluran_ComanderAI2 extends AggressiveNpcAI2
{
	private AtomicBoolean startedEvent = new AtomicBoolean(false);
	
	@Override
	protected void handleCreatureMoved(Creature creature) {
		if (creature instanceof Player) {
			final Player player = (Player) creature;
			if (MathUtil.getDistance(getOwner(), player) <= 15) {
				if (startedEvent.compareAndSet(false, true)) {
					//Victory is a certainty for us Elyos, yet you continue your futile struggles.
				    sendMsg(1501537, getObjectId(), false, 3000);
					//You're no fools. Haven't you yet realized that this isn't a battle you can win?
				    sendMsg(1501538, getObjectId(), false, 9000);
					//Are there really this many Asmodians ignorant of their fate…?
					//Then I'll teach you here and now.
					//That today is your last day alive!
				    sendMsg(1501539, getObjectId(), false, 15000);
					//Lord Ariel! Please show your power to that Asmodian!
				    sendMsg(1501541, getObjectId(), false, 21000);
				}
			}
		}
	}
	
	@Override
	protected void handleDied() {
		RvrService.getInstance().stopRvr(4);
		spawn(833766, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0); //Dimensional Vortex.
		super.handleDied();
	}
	
	private void sendMsg(int msg, int Obj, boolean isShout, int time) {
		NpcShoutsService.getInstance().sendMsg(getPosition().getWorldMapInstance(), msg, Obj, isShout, 0, time);
	}
}