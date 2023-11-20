package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class NameColor extends CommandCompat{
    public NameColor(){
        super("namecolor", false, false, new String[]{"namecolour"});
    }

    public String commandSyntax(final String commandUsed){
        String ret = ChatColors.YELLOW + commandUsed + " <color>\n" + ChatColors.YELLOW + "Available colors:\n";

        for(Map.Entry<String, String> entry : KivaServerUtils.nameColorChoicesNames.entrySet())
            ret += entry.getValue() + entry.getKey() + "\n";

        return ret;
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(this.commandSyntax(args[0]), commandExecutor);
            return;
        }

        String newColor = KivaServerUtils.nameColorChoicesNames.get(args[1].toLowerCase());

        if (newColor == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "Invalid color");
            return;
        }

        KivaServerUtils.playerNameColors.put(commandExecutor.getPlayerName(), newColor);
        commandExecutor.displayChatMessage("Name color set to " + newColor + args[1].toLowerCase() + ChatColors.RESET + "!");
    }
}
