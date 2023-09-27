package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class HomeCommandsDisabled extends CommandCompat{
    public HomeCommandsDisabled(){
        super("homecommandsdisabled", true);
    }

    public String commandSyntax(){
        return "§e/homecommandsdisabled <true or false>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        if (!(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false"))){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        boolean value = args[1].equalsIgnoreCase("true");

        KivaServerUtils.config.put("homecommandsdisabled", value);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "homecommandsdisabled now set to: " + ChatColors.RESET + value);
        commandExecutor.displayChatMessage(ChatColors.YELLOW + "/home and /sethome are " + (value ? "no longer" : "now") + " usable");
    }
}
