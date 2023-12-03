package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class PronounsListAll extends CommandCompat{
    public PronounsListAll(){
        super("pronounslistall", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/pronounslistall";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.playerPronouns.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no player pronouns");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.BLUE + "Pronouns list:" + ChatColors.RESET);

        for (String playerName : KivaServerUtils.playerPronouns.keySet()){
            commandExecutor.displayChatMessage(playerName + ChatColors.BLUE + " : " + ChatColors.GRAY + KivaServerUtils.playerPronouns.get(playerName));
        }
    }
}
