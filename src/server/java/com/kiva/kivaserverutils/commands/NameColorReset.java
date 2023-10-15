package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class NameColorReset extends CommandCompat{
    public NameColorReset(){
        super("namecolorreset", false);
    }

    public String commandSyntax(){
        return "§e/namecolorreset";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        KivaServerUtils.playerNameColors.remove(commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage("Name color reset!");
    }
}
