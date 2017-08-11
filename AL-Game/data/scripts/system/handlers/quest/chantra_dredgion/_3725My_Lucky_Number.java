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
package quest.chantra_dredgion;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.*;
import com.aionemu.gameserver.questEngine.model.*;

/****/
/** Author Rinzler (Encom)
/****/

public class _3725My_Lucky_Number extends QuestHandler
{
	private static final int questId = 3725;
	
	public _3725My_Lucky_Number() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerOnDredgionReward(questId);
		qe.registerQuestNpc(799069).addOnQuestStart(questId); //Yannis.
		qe.registerQuestNpc(799069).addOnTalkEvent(questId); //Yannis.
		qe.registerQuestNpc(798928).addOnTalkEvent(questId); //Yulia.
		qe.registerQuestNpc(216866).addOnKillEvent(questId); //Chantra Legatus.
	}
	
	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		QuestDialog dialog = env.getDialog();
		int targetId = env.getTargetId();
		int var1 = qs.getQuestVarById(1);
		int var2 = qs.getQuestVarById(2);
		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (targetId == 799069) { //Yannis.
				if (dialog == QuestDialog.START_DIALOG) {
					return sendQuestDialog(env, 4762);
				} else {
					return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 798928) { //Yulia.
				if (dialog == QuestDialog.START_DIALOG) {
					if (var1 == 6 && var2 == 15) {
						return sendQuestDialog(env, 10002);
					}
				} else if (dialog == QuestDialog.SELECT_REWARD) {
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(env);
					return sendQuestDialog(env, 5);
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798928) { //Yulia.
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
	
	@Override
	public boolean onKillEvent(QuestEnv env) {
		int[] mobs = {216866}; //Chantra Legatus.
		return defaultOnKillEvent(env, mobs, 0, 15, 2);
	}
	
	@Override
	public boolean onDredgionRewardEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var1 = qs.getQuestVarById(1);
			if (var1 < 6) {
				changeQuestStep(env, var1, var1 + 1, false, 1);
				return true;
			}
		}
		return false;
	}
}