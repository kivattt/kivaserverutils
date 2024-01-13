package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.NicknameAllowed;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class NickSet extends CommandCompat{
    public NickSet(){
        super("nickset", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/nickset <player> <nickname>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (!commandExecutor.isOperator()){
            commandExecutor.displayChatMessage(ChatColors.RED + "This command is for operators only, did you mean " + ChatColors.YELLOW + "/nick" + ChatColors.RED + "?");
            return;
        }

        if (args.length != 3){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }

        String nickname = args[2];
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

        if (!NicknameAllowed.nicknameIsAllowed(nickname, args[1])){
            commandExecutor.displayChatMessage(ChatColors.RED + "Someone else already has that nickname/username");
            return;
        }

        if (ServerMod.getGameInstance().configManager.getPlayerEntity(args[1]) == null)
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "Player " + ChatColors.RESET + args[1] + ChatColors.YELLOW + " is not on the server");

        KivaServerUtils.playerNicknames.put(args[1], nickname);
        ServerMod.getGameInstance().configManager.sendChatMessageToAllOps(KivaServerUtils.KSUBroadcastPrefix + ChatColors.GREEN + "Nickname of " + ChatColors.RESET + args[1] + ChatColors.GREEN + " set to " + ChatColors.RESET + nickname);
    }
}
