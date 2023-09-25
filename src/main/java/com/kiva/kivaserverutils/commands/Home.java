package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.KivaServerUtils.spawnCommandLocation;

public class Home extends CommandCompat{
    public Home(){
        super("home", false);
    }

    public String commandSyntax(){
        return "§e/home";
    }

    // TODO Make DRY
    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.playerHomes == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no home, use /sethome");
            return;
        }

        Coordinate homeCoordinate = KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName());
        if (homeCoordinate == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no home, use /sethome");
            return;
        }

        commandExecutor.teleportRegistered(homeCoordinate.x, homeCoordinate.y, homeCoordinate.z);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Teleported to home!");
    }
}
