package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class PronounsList extends CommandCompat{
    public PronounsList(){
        super("pronounslist", false);
    }

    public String commandSyntax(){
        return "§e/pronounslist";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.BLUE + "Pronouns list:" + ChatColors.RESET);

        for (String playerName : KivaServerUtils.playerPronouns.keySet()){
            commandExecutor.displayChatMessage(playerName + ChatColors.BLUE + " : " + ChatColors.GRAY + KivaServerUtils.playerPronouns.get(playerName));
        }
    }
}
