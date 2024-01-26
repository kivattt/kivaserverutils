package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class NameColorReset extends CommandCompat{
    public NameColorReset(){
        super("namecolorreset", false, false, new String[]{"namecolourreset"});
    }

    public String commandSyntax(final String commandUsed){
        return ChatColors.YELLOW + commandUsed;
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        KivaServerUtils.playerNameColors.remove(commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage("Name color reset!");
    }
}
