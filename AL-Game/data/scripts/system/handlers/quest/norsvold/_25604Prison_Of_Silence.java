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
package quest.norsvold;

import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.*;
import com.aionemu.gameserver.questEngine.handlers.*;
import com.aionemu.gameserver.questEngine.model.*;
import com.aionemu.gameserver.services.*;
import com.aionemu.gameserver.services.teleport.*;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.zone.*;

/****/
/** Author Rinzler (Encom)
/****/

public class _25604Prison_Of_Silence extends QuestHandler
{
    public static final int questId = 25604;
	private final static int[] DF6FOBJSacrificeGuardian01 = {703125};
	
    public _25604Prison_Of_Silence() {
        super(questId);
    }
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 25602);
	}
	
    @Override
    public void register() {
		qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(806115).addOnQuestStart(questId); //Svanhild.
		qe.registerQuestNpc(806115).addOnTalkEvent(questId); //Svanhild.
		qe.registerQuestNpc(806173).addOnTalkEvent(questId); //Groma.
		for (int mobs: DF6FOBJSacrificeGuardian01) {
            qe.registerQuestNpc(mobs).addOnKillEvent(questId);
        }
		qe.registerOnEnterZone(ZoneName.get("DF6_SENSORY_AREA_Q25604_A_DYNAMIC_ENV_220110000"), questId);
    }
	
    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
		int var = qs.getQuestVarById(0);
		int targetId = env.getTargetId();
		final Npc npc = (Npc) env.getVisibleObject();
		if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        } if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 806115) { //Svanhild.
                switch (env.getDialog()) {
                    case START_DIALOG: {
                        return sendQuestDialog(env, 4762);
					} case ACCEPT_QUEST:
					case ACCEPT_QUEST_SIMPLE: {
						return sendQuestStartDialog(env);
					} case REFUSE_QUEST_SIMPLE: {
				        return closeDialogWindow(env);
					}
                }
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 806173) { //Groma.
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						} else if (var == 3) {
							return sendQuestDialog(env, 2034);
						} else if (var == 4) {
							return sendQuestDialog(env, 2375);
						}
					} case SELECT_ACTION_1012: {
						if (var == 0) {
							return sendQuestDialog(env, 1012);
						}
					} case STEP_TO_1: {
						playQuestMovie(env, 874);
						changeQuestStep(env, 0, 1, false);
						TeleportService2.teleportTo(player, 220110000, 1254.2067f, 2899.5266f, 251.91998f, (byte) 118);
						return closeDialogWindow(env);
					} case STEP_TO_4: {
						changeQuestStep(env, 3, 4, false);
						TeleportService2.teleportTo(player, 220110000, 1254.2067f, 2899.5266f, 251.91998f, (byte) 118);
						return closeDialogWindow(env);
					} case CHECK_COLLECTED_ITEMS: {
						if (QuestService.collectItemCheck(env, true)) {
							changeQuestStep(env, 4, 5, true);
							return sendQuestDialog(env, 10000);
						} else {
							return sendQuestDialog(env, 10001);
						}
					}
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 806115) { //Svanhild.
                if (env.getDialog() == QuestDialog.START_DIALOG) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
        return false;
    }
	
	@Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 2) {
                int var1 = qs.getQuestVarById(1);
                if (var1 >= 0 && var1 < 2) {
                    return defaultOnKillEvent(env, DF6FOBJSacrificeGuardian01, var1, var1 + 1, 1);
                } else if (var1 == 2) {
					qs.setQuestVar(3);
					updateQuestStatus(env);
                    return true;
                }
            }
        }
        return false;
    }
	
	@Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
			if (zoneName == ZoneName.get("DF6_SENSORY_AREA_Q25604_A_DYNAMIC_ENV_220110000")) {
				if (var == 1) {
					changeQuestStep(env, 1, 2, false);
					return true;
				}
			}
		}
		return false;
	}
}