package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.NicknameAllowed;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Nick extends CommandCompat{
    public Nick(){super("nick", false);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/nick <nickname>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }

        String nickname = args[1];
        String nicknameLowerCase = nickname.toLowerCase();

        if (nickname.length() > 16){
            commandExecutor.displayChatMessage(ChatColors.RED + "Nickname too long, max length is 16");
            return;
        }

        String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789_-";

        for (int i = 0; i < nicknameLowerCase.length(); i++){
            if (allowedChars.indexOf(nicknameLowerCase.charAt(i)) == -1){
                commandExecutor.displayChatMessage(ChatColors.RED + "Disallowed character(s), only a-z (case-insensitive), 0-9 and symbols -_ allowed");
                return;
            }
        }

        if (!NicknameAllowed.nicknameIsAllowed(nickname, commandExecutor.getPlayerName())){
            commandExecutor.displayChatMessage(ChatColors.RED + "Someone else already has that nickname/username");
            return;
        }

        KivaServerUtils.playerNicknames.put(commandExecutor.getPlayerName(), nickname);
        ServerMod.getGameInstance().configManager.sendChatMessageToAllOps(KivaServerUtils.KSUBroadcastPrefix + ChatColors.GREEN + "Nickname of " + ChatColors.RESET + commandExecutor.getPlayerName() + ChatColors.GREEN + " set to " + ChatColors.RESET + nickname);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Nickname set to " + ChatColors.RESET + nickname);
    }
}
