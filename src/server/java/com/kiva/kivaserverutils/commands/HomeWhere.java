package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

public class HomeWhere extends CommandCompat{
    public HomeWhere(){
        super("homewhere", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/homewhere";
    }

    // TODO Make DRY
    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("homecommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Home commands are disabled");
            return;
        }

        if (KivaServerUtils.playerHomes == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no home, use /sethome");
            return;
        }

        Coordinate homeCoordinate = KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName());
        if (homeCoordinate == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no home, use /sethome");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.GREEN + "Your home is at [" + ChatColors.RESET + homeCoordinate.toStringXYZInt() + ChatColors.GREEN + "] in the " + ChatColors.RESET + Coordinate.dimensionToString(homeCoordinate.dimension));
    }
}
