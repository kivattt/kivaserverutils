package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class WarpRename extends CommandCompat{
    public WarpRename(){
        super("warprename", true);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/warprename <old name> <new name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.getConfigValue("warpcommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Warp commands are disabled");
            return;
        }

        if (KivaServerUtils.warps == null || KivaServerUtils.warps.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no warps on this server");
            return;
        }

        if (args.length != 3){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        String oldWarpName = args[1];
        String newWarpName = args[2];

        if (oldWarpName.isEmpty() || newWarpName.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't specify an empty warp name");
            commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
            return;
        }

        if (!KivaServerUtils.warps.containsKey(oldWarpName)){
            commandExecutor.displayChatMessage(ChatColors.RED + "There is no warp named " + ChatColors.RESET + oldWarpName);
            return;
        }

        if (oldWarpName.equals(newWarpName)){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "Name unchanged");
            return;
        }

        if (KivaServerUtils.warps.containsKey(newWarpName)){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "That warp already exists, nothing done");
            return;
        }

        if (newWarpName.length() > 15){
            commandExecutor.displayChatMessage(ChatColors.RED + "New warp name too long, max length is 15");
            return;
        }

        String newWarpNameLowercase = newWarpName.toLowerCase();

        String allowedChars = "-'!abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < newWarpNameLowercase.length(); i++){
            if (allowedChars.indexOf(newWarpNameLowercase.charAt(i)) == -1){
                commandExecutor.displayChatMessage(ChatColors.RED + "Disallowed character(s), only a-z, A-Z and -!' allowed");
                return;
            }
        }

        Coordinate oldWarpToMove = KivaServerUtils.warps.get(oldWarpName);
        KivaServerUtils.warps.put(newWarpName, oldWarpToMove);
        KivaServerUtils.warps.remove(oldWarpName); // In this order to make sure the warp is never removed
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Warp " + ChatColors.RESET + oldWarpName + ChatColors.GREEN + " renamed: " + ChatColors.RESET + newWarpName);
    }
}
