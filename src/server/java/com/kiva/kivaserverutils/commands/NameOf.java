package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.NicknameToUsername;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class NameOf extends CommandCompat {
    public NameOf(){super("nameof", false);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/nameof <nickname>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (args.length != 2){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }
        String usernameFound = NicknameToUsername.nicknameToUsername(args[1], "");

        if (usernameFound == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "No player found for that nickname");
            return;
        }

        commandExecutor.displayChatMessage(usernameFound + ChatColors.GREEN + " has that nickname");
    }
}
