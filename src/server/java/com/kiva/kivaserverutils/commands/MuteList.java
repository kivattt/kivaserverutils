package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class MuteList extends CommandCompat {
    public MuteList(){super("mutelist", false);}

    public String commandSyntax(){return ChatColors.YELLOW + "/mutelist";}

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.playersMuted.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no muted players");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.BLUE + "Players muted:");

        for (String playerName : KivaServerUtils.playersMuted) {
            if (ServerMod.getGameInstance().configManager.getPlayerEntity(playerName) != null)
                commandExecutor.displayChatMessage(playerName);
        }
    }
}
