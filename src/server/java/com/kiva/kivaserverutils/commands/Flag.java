package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Flag extends CommandCompat{
    public Flag(){super("flag", false);}

    public String commandSyntax(){
        StringBuilder ret = new StringBuilder(ChatColors.YELLOW + "Available colors:\n");

        for (Map.Entry<String, String> entry : KivaServerUtils.flagColorChoicesNames.entrySet())
            ret.append(entry.getValue()).append(entry.getKey()).append("\n");

        ret.append(ChatColors.YELLOW + "Example: /flag lightred orange yellow green blue purple");

        return ret.toString();
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length <= 1){
            sendUsageMessage(commandSyntax(), commandExecutor, false);
            return;
        }

        if (args.length > 7){
            commandExecutor.displayChatMessage(ChatColors.RED + "The max number of colors in a flag is 6");
            return;
        }

        StringBuilder flag = new StringBuilder();
        for (int i = 1; i < args.length; i++){
            String colorName = args[i].trim().toLowerCase();
            if (colorName.isEmpty()){
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't use an empty argument");
                commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
                return;
            }

            String colorFound = KivaServerUtils.flagColorChoicesNames.get(colorName);
            if (colorFound == null){
                commandExecutor.displayChatMessage(ChatColors.RED + "There is no color named \"" + ChatColors.RESET + colorName + ChatColors.RED + "\"");
                return;
            }

            flag.append(colorFound).append(KivaServerUtils.flagDataCharacterToBeReplaced);
        }


        KivaServerUtils.playerFlags.put(commandExecutor.getPlayerName(), flag.toString());
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Flag set to " + ChatColors.RESET + KivaServerUtils.getPlayerFlag(commandExecutor.getPlayerName()));
    }
}
