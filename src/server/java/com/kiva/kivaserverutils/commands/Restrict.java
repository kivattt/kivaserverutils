package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Restrict extends CommandCompat {
    public Restrict(){super("restrict", true);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/restrict <player>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        String playerUsername = args[1];
        Boolean newRestrictiveMode = KivaServerUtils.togglePlayerRestrictiveMode(playerUsername);

        ServerMod.getGameInstance().configManager.sendChatMessageToAllOps(ChatColors.GREEN + "Player " + ChatColors.RESET + playerUsername + " " + (newRestrictiveMode ? ChatColors.RED + "is now" : ChatColors.GREEN + "is no longer") + " in restrictive mode");
    }
}
