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
package ai.beritraInvasion;

import java.util.*;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.*;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.landing.*;
import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.services.*;
import com.aionemu.gameserver.services.abyss.*;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.*;
import com.aionemu.gameserver.world.knownlist.Visitor;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("terracrusher")
public class TerracrusherAI2 extends AggressiveNpcAI2
{
	@Override
	protected void handleDied() {
		addGpPlayer();
		announceEreshkigalDie();
		announceKilledEreshkigal();
		updateTerracrusherLanding();
		super.handleDied();
	}
	
	private void addGpPlayer() {
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (MathUtil.isIn3dRange(player, getOwner(), 15)) {
					AbyssPointsService.addGp(player, 500);
				}
			}
		});
	}
	private void announceEreshkigalDie() {
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				//The Ereshkigal Legion's magic weapon has been destroyed.
				PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_WORLDRAID_Ere_MESSAGE_DIE_01);
			}
		});
	}
	private void announceKilledEreshkigal() {
		Npc npc = (Npc) getOwner();
		final DescriptionId NameId = new DescriptionId(npc.getObjectTemplate().getNameId());
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player players) {
				AionObject winner = getAggroList().getMostDamage();
				if (winner instanceof Creature) {
					final Creature kill = (Creature) winner;
					//%0 has destroyed %0 and the Landing is now enhanced.
					AbyssLandingService.getInstance().AnnounceToPoints(players, kill.getRace().getRaceDescriptionId(), NameId, 0, LandingPointsEnum.MONUMENT);
				}
			}
		});
	}
	
	private void updateTerracrusherLanding() {
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (MathUtil.isIn3dRange(getOwner().getAggroList().getMostHated(), getOwner(), 20)) {
					if (getOwner().getAggroList().getPlayerWinnerRace() == Race.ASMODIANS) {
						AbyssLandingService.getInstance().onRewardMonuments(Race.ASMODIANS, 24, 10000);
					} else if (getOwner().getAggroList().getPlayerWinnerRace() == Race.ELYOS) {
						AbyssLandingService.getInstance().onRewardMonuments(Race.ELYOS, 12, 10000);
					}
				}
			}
		});
	}
}