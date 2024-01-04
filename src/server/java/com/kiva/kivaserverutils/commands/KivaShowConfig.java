package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

public class KivaShowConfig extends CommandCompat{
    public KivaShowConfig(){
        super("kivashowconfig", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/kivashowconfig";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.config.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "KivaServerUtils config is empty");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.GREEN + "KivaServerUtils config:" + ChatColors.RESET);
        for (Map.Entry<String, Boolean> entry : KivaServerUtils.config.entrySet())
            commandExecutor.displayChatMessage(entry.getKey() + " = " + (entry.getValue() ? ChatColors.DARK_RED : ChatColors.GRAY) + entry.getValue());
    }
}
