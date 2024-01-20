package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class PronounsColorReset extends CommandCompat{
    public PronounsColorReset(){
        super("pronounscolorreset", false, false, new String[]{"pronounscolourreset"});
    }

    public String commandSyntax(final String commandUsed){
        return ChatColors.YELLOW + commandUsed;
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        KivaServerUtils.playerPronounColors.remove(commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage("Pronouns color reset!");
    }
}
