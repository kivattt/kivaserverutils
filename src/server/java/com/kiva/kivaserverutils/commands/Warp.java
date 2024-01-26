package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Warp extends CommandCompat{
    public Warp(){
        super("warp", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/warp <name>\n" + ChatColors.YELLOW + "Type " + ChatColors.GREEN + "/warps" + ChatColors.YELLOW + " for a list of warps";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("warpcommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Warp commands are disabled");
            return;
        }

        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        String warpName = args[1];

        if (KivaServerUtils.warps == null || KivaServerUtils.warps.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no warps on this server");
            return;
        }

        if (warpName.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't specify an empty warp name");
            commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
            return;
        }

        Coordinate warp = KivaServerUtils.warps.get(warpName);
        if (warp == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "There is no warp named \"" + ChatColors.RESET + warpName + ChatColors.RED + "\"");
            return;
        }

        if (warp.dimension != ServerMod.toEntityPlayerMP(commandExecutor).dimension)
            commandExecutor.sendPlayerThroughPortalRegistered();

        commandExecutor.teleportRegistered(warp.x, warp.y, warp.z);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Warped to " + ChatColors.RESET + warpName + ChatColors.GREEN + "!");
    }
}
