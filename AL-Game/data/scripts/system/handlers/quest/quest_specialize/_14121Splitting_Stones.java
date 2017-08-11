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
package quest.quest_specialize;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.*;
import com.aionemu.gameserver.questEngine.model.*;

/****/
/** Author Rinzler (Encom)
/****/

public class _14121Splitting_Stones extends QuestHandler
{
    private final static int questId = 14121;
	
    public _14121Splitting_Stones() {
        super(questId);
    }
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env);
	}
	
    @Override
    public void register() {
		qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(203903).addOnQuestStart(questId); //Valerius
        qe.registerQuestNpc(203903).addOnTalkEvent(questId); //Valerius
		qe.registerQuestNpc(204032).addOnTalkEvent(questId); //Lakaias
    }
	
	@Override
    public boolean onDialogEvent(final QuestEnv env) {
        final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
		int var = qs.getQuestVarById(0);
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203903) { //Valerius
			    if (env.getDialog() == QuestDialog.START_DIALOG) {
				   return sendQuestDialog(env, 1011);
			    } else {
				   return sendQuestStartDialog(env);
			    }
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 204032) { //Lakaias
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1352);
						} else if (var == 1) {
							return sendQuestDialog(env, 2375);
						}
					} case STEP_TO_1: {
						return defaultCloseDialog(env, 0, 1);
					} case CHECK_COLLECTED_ITEMS_SIMPLE: {
						return checkQuestItems(env, 1, 1, true, 5, 2716);
					} case FINISH_DIALOG: {
                        return sendQuestSelectionDialog(env);
                    }
				}
			}
        } else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204032) { //Lakaias
                return sendQuestEndDialog(env);
			}
		}
        return false;
    }
}