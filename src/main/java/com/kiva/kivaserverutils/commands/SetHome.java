package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

public class SetHome extends CommandCompat{
    public SetHome(){
        super("sethome", false);
    }

    public String commandSyntax(){
        return "§e/sethome";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        Coordinate playersCurrentCoordinate = new Coordinate();
        playersCurrentCoordinate.x = commandExecutor.getRegisteredX();
        playersCurrentCoordinate.y = commandExecutor.getRegisteredY();
        playersCurrentCoordinate.z = commandExecutor.getRegisteredZ();

        if (playersCurrentCoordinate.y < 1){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't set a home below y=1");
            return;
        }

        KivaServerUtils.playerHomes.put(commandExecutor.getPlayerName(), playersCurrentCoordinate);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Home location set! [" + ChatColors.RESET + (int)playersCurrentCoordinate.x + " " + (int)playersCurrentCoordinate.y + " " + (int)playersCurrentCoordinate.z + ChatColors.GREEN + "]" + ChatColors.RESET);
    }
}
