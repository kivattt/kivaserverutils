package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
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
        if (KivaServerUtils.getConfigValue("homecommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Home commands are disabled");
            return;
        }

        Coordinate playersCurrentCoordinate = new Coordinate();
        playersCurrentCoordinate.x = commandExecutor.getRegisteredX();
        playersCurrentCoordinate.y = commandExecutor.getRegisteredY();
        playersCurrentCoordinate.z = commandExecutor.getRegisteredZ();
        playersCurrentCoordinate.dimension = ServerMod.toEntityPlayerMP(commandExecutor).dimension;

        if (playersCurrentCoordinate.y < 1){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't set a home below y=1");
            return;
        }

        KivaServerUtils.playerHomes.put(commandExecutor.getPlayerName(), playersCurrentCoordinate);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Home location set! [" + ChatColors.RESET + playersCurrentCoordinate.toStringXYZInt() + ChatColors.GREEN + "] in the " + ChatColors.RESET + Coordinate.dimensionToString(playersCurrentCoordinate.dimension));
    }
}
