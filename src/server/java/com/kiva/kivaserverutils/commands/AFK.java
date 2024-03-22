package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.server.packets.Packet3Chat;

public class AFK extends CommandCompat {
    public AFK() {
        super("afk", false);
    }

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/afk";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (KivaServerUtils.afkPlayers.containsKey(commandExecutor.getPlayerName())) {
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "You are already AFK");
            return;
        }

        KivaServerUtils.afkPlayers.put(commandExecutor.getPlayerName(), System.currentTimeMillis());
        ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(ChatColors.GRAY + commandExecutor.getPlayerName() + " is now AFK"));
    }
}
