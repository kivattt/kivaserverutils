package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class RenameHome extends CommandCompat{
    public RenameHome(){
        super("renamehome", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/renamehome <old name> <new name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.getConfigValue("homecommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Home commands are disabled");
            return;
        }

        if (args.length != 3){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        if (KivaServerUtils.playerHomes == null || KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()) == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no homes, use /sethome");
            return;
        }

        String oldHomeName = args[1];
        String newHomeName = args[2];

        if (oldHomeName.isEmpty() || newHomeName.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't specify an empty home name");
            commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
            return;
        }

        if (!KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).containsKey(oldHomeName)){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no home named " + ChatColors.RESET + oldHomeName);
            return;
        }

        if (oldHomeName.equals(newHomeName)){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "Name unchanged");
            return;
        }

        if (KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).containsKey(newHomeName)){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "That home already exists, nothing done");
            return;
        }

        if (newHomeName.length() > 15){
            commandExecutor.displayChatMessage(ChatColors.RED + "New home name too long, max length is 15");
            return;
        }

        String newHomeNameLowercase = newHomeName.toLowerCase();

        String allowedChars = "-'!abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < newHomeNameLowercase.length(); i++){
            if (allowedChars.indexOf(newHomeNameLowercase.charAt(i)) == -1){
                commandExecutor.displayChatMessage(ChatColors.RED + "Disallowed character(s), only a-z, A-Z and -!' allowed");
                return;
            }
        }

        Coordinate oldWarpToMove = KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).get(oldHomeName);
        KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).put(newHomeName, oldWarpToMove);
        KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).remove(oldHomeName);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Home " + ChatColors.RESET + oldHomeName + ChatColors.GREEN + " renamed: " + ChatColors.RESET + newHomeName);
    }
}
