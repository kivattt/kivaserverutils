package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Warps extends CommandCompat{
    public Warps(){
        super("warps", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/warps <optional name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("warpcommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Warp commands are disabled");
            return;
        }

        if (KivaServerUtils.warps == null || KivaServerUtils.warps.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no warps on this server");
            return;
        }

        // Name specified, only show that
        if (args.length == 2){
            String warpName = args[1];

            if (warpName.isEmpty()){ // Probably an impossible case, but let's be diligent
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't specify an empty warp name");
                commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
                return;
            }

            Coordinate warp = KivaServerUtils.warps.get(warpName);
            if (warp == null){
                commandExecutor.displayChatMessage(ChatColors.RED + "There is no warp named \"" + ChatColors.RESET + warpName + ChatColors.RED + "\"");
                return;
            }

            commandExecutor.displayChatMessage(ChatColors.BLUE + warpName + ChatColors.RESET + " : " + warp.toStringXYZInt() + ChatColors.DARK_GRAY + " in the " + ChatColors.GRAY + Coordinate.dimensionToString(warp.dimension));
            return;
        } else if (args.length > 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.DARK_AQUA + "Warps:");
        for (Map.Entry<String, Coordinate> warp : KivaServerUtils.warps.entrySet())
            commandExecutor.displayChatMessage(ChatColors.BLUE + warp.getKey() + ChatColors.RESET + " : " + warp.getValue().toStringXYZInt() + ChatColors.DARK_GRAY + " in the " + ChatColors.GRAY + Coordinate.dimensionToString(warp.getValue().dimension));
    }
}
