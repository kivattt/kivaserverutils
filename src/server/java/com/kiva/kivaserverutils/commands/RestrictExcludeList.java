package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class RestrictExcludeList extends CommandCompat {
    public RestrictExcludeList(){super("restrictexcludelist", true);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/restrictexcludelist";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.playersExcludedFromRestrictiveMode.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no players excluded from restrictive mode");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.BLUE + "Players excluded from restrictive mode:");

        for (String playerName : KivaServerUtils.playersExcludedFromRestrictiveMode) {
            if (ServerMod.getGameInstance().configManager.getPlayerEntity(playerName) != null)
                commandExecutor.displayChatMessage(playerName);
        }
    }
}
