package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class NameColor extends CommandCompat{
    public NameColor(){
        super("namecolor", false);
    }

    public String commandSyntax(){
        String ret = "§e/namecolor <color>\n§eAvailable colors:\n";

        for(Map.Entry<String, String> entry : KivaServerUtils.colorNames.entrySet())
            ret += entry.getValue() + entry.getKey() + "\n";

        return ret;
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }

        String newColor = KivaServerUtils.colorNames.get(args[1].toLowerCase());

        if (newColor == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "Invalid color");
            return;
        }

        KivaServerUtils.playerNameColors.put(commandExecutor.getPlayerName(), newColor);
        commandExecutor.displayChatMessage("Name color set to " + newColor + args[1].toLowerCase() + ChatColors.RESET + "!");
    }
}
