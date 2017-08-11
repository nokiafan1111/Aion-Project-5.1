package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.*;

public class CM_INSTANCE_INFO extends AionClientPacket
{
	private static Logger log = LoggerFactory.getLogger(CM_INSTANCE_INFO.class);
	
    @SuppressWarnings("unused")
    private int unk1, unk2;
	
    public CM_INSTANCE_INFO(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }
	
    @Override
    protected void readImpl() {
        unk1 = readD();
        unk2 = readC();
    }
	
    @Override
    protected void runImpl() {
        if (unk2 == 1 && !getConnection().getActivePlayer().isInTeam()) {
			log.debug("Received CM_INSTANCE_INFO with teamdata request but player has no team!");
        } if (unk2 == 1) {
            Player player = getConnection().getActivePlayer();
            if (player.isInAlliance2()) {
                boolean answer = true;
                for (Player p: player.getPlayerAlliance2().getMembers()) {
                    if (answer) {
                        PacketSendUtility.sendPacket(p, new SM_INSTANCE_INFO(p, true, p.getCurrentTeam()));
                        answer = false;
                    } else {
                        PacketSendUtility.sendPacket(p, new SM_INSTANCE_INFO(p, false, p.getCurrentTeam()));
                    }
                }
            } else if (player.isInGroup2()) {
                boolean answer = true;
                for (Player p: player.getPlayerGroup2().getMembers()) {
                    if (answer) {
                        PacketSendUtility.sendPacket(p, new SM_INSTANCE_INFO(p, true, p.getCurrentTeam()));
                        answer = false;
                    } else {
                        PacketSendUtility.sendPacket(p, new SM_INSTANCE_INFO(p, false, p.getCurrentTeam()));
                    }
                }
            }
        } else {
            sendPacket(new SM_INSTANCE_INFO(getConnection().getActivePlayer(), true, getConnection().getActivePlayer().getCurrentTeam()));
		}
    }
}