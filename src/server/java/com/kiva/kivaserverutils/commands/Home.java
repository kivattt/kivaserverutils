package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

public class Home extends CommandCompat{
    public Home(){
        super("home", false);
    }

    public String commandSyntax(){
        return "§e/home";
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

        if (homeCoordinate.dimension != ServerMod.toEntityPlayerMP(commandExecutor).dimension){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "Your home is in the " + Coordinate.dimensionToString(homeCoordinate.dimension) + ", unable to teleport you");
            return;
        }

        commandExecutor.teleportRegistered(homeCoordinate.x, homeCoordinate.y, homeCoordinate.z);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Teleported to home!");
    }
}
