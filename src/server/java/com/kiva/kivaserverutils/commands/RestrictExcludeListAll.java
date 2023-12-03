package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class RestrictExcludeListAll extends CommandCompat {
    public RestrictExcludeListAll(){super("restrictexcludelistall", true);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/restrictexcludelistall";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.playersExcludedFromRestrictiveMode.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no players excluded from restrictive mode");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.BLUE + "Players excluded from restrictive mode:");

        for (String playerName : KivaServerUtils.playersExcludedFromRestrictiveMode)
            commandExecutor.displayChatMessage(playerName);
    }
}
