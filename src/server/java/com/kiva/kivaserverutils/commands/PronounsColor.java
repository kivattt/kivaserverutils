package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class PronounsColor extends CommandCompat{
    public PronounsColor(){
        super("pronounscolor", false, false, new String[]{"pronounscolour"});
    }

    public String commandSyntax(final String commandUsed){
        StringBuilder ret = new StringBuilder(ChatColors.YELLOW + commandUsed + " <color>\n" + ChatColors.YELLOW + "Available colors:\n");

        for (Map.Entry<String, String> entry : KivaServerUtils.pronounColorChoicesNames.entrySet())
            ret.append(entry.getValue()).append(entry.getKey()).append("\n");

        return ret.toString();
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(this.commandSyntax(args[0]), commandExecutor);
            return;
        }

        String newColor = KivaServerUtils.pronounColorChoicesNames.get(args[1].toLowerCase());

        if (newColor == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "Invalid color");
            return;
        }

        KivaServerUtils.playerPronounColors.put(commandExecutor.getPlayerName(), newColor);
        commandExecutor.displayChatMessage("Pronouns color set to " + newColor + args[1].toLowerCase() + ChatColors.RESET + "!");
    }
}
