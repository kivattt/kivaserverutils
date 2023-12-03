package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class RestrictListAll extends CommandCompat {
    public RestrictListAll(){super("restrictlistall", true);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/restrictlistall";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.playersInRestrictiveMode.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no players in restrictive mode");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.BLUE + "Players in restrictive mode:" + ChatColors.RESET);

        for (String playerName : KivaServerUtils.playersInRestrictiveMode)
            commandExecutor.displayChatMessage(playerName);
    }
}
