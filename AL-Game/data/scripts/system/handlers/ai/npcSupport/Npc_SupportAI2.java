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
package ai.npcSupport;

import ai.GeneralNpcAI2;

import com.aionemu.commons.utils.Rnd;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.controllers.effect.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("npc_support")
public class Npc_SupportAI2 extends GeneralNpcAI2
{
    @Override
	protected void handleDialogStart(Player player) {
        switch (getNpcId()) {
            //Elyos.
			case 831024: //Ryoenniya.
			case 831025: //Luella.
			case 831030: //Netalion.
			case 831031: //Nebrith.
            //Asmodians.
            case 831026: //Rikanellie.
			case 831027: //Karzanke.
			case 831028: //Erdat.
			case 831029: { //Edandos.
				super.handleDialogStart(player);
				break;
			} default: {
				PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
				break;
			}
		}
	}
	
	@Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
		QuestEnv env = new QuestEnv(getOwner(), player, questId, dialogId);
		env.setExtendedRewardIndex(extendedRewardIndex);
		PlayerEffectController effectController = player.getEffectController();
		if (QuestEngine.getInstance().onDialog(env) && dialogId != 1011) {
			return true;
		} if (dialogId == 10000) {
			int skillId = 0;
			switch (getNpcId()) {
			    case 831024: //Ryoenniya.
			    case 831025: //Luella.
			    case 831030: //Netalion.
				case 831031: //Nebrith.
				case 831026: //Rikanellie.
			    case 831027: //Karzanke.
			    case 831028: //Erdat.
				case 831029: //Edandos.
				    switch (Rnd.get(1, 2)) {
						case 1:
							skillId = 20950; //Blessing Of Growth.
							effectController.removeEffect(20951);
						break;
						case 2:
							skillId = 20951; //Blessing Of Guardianship.
							effectController.removeEffect(20950);
						break;
					}
				break;
			}
			SkillEngine.getInstance().getSkill(getOwner(), skillId, 1, player).useNoAnimationSkill();
			player.getLifeStats().setCurrentHpPercent(100); //charge full hp
            player.getLifeStats().setCurrentMpPercent(100); //charge full mp
		} else if (dialogId == 1011 && questId != 0) {
			PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), dialogId, questId));
		}
        return true;
    }
}