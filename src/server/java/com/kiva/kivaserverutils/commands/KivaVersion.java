package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

public class KivaVersion extends CommandCompat{
    public KivaVersion(){
        super("kivaversion", false);
    }

    public String commandSyntax(){
        return "§e/kivaversion";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        commandExecutor.displayChatMessage(ChatColors.GREEN + "KivaServerUtils" + ChatColors.RESET + " is on version " + ChatColors.YELLOW +  KivaServerUtils.version + ChatColors.RESET);
    }
}
