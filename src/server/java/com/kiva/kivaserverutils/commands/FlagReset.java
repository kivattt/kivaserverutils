package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class FlagReset extends CommandCompat{
    public FlagReset(){super("flagreset", false);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/flagreset";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        KivaServerUtils.playerFlags.remove(commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Flag reset!");
    }
}
