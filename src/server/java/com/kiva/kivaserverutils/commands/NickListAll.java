package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class NickListAll extends CommandCompat{
    public NickListAll(){
        super("nicklistall", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/nicklistall";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.playerNicknames.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no player nicknames");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.DARK_AQUA + "All players nicknames:" + ChatColors.RESET);

        for (String playerName : KivaServerUtils.playerNicknames.keySet()){
            commandExecutor.displayChatMessage(playerName + ChatColors.DARK_AQUA + " : " + ChatColors.GRAY + KivaServerUtils.playerNicknames.get(playerName));
        }
    }
}
