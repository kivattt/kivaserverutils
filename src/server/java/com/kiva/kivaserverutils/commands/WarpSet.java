package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class WarpSet extends CommandCompat{
    public WarpSet(){
        super("warpset", true);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/warpset <name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.getConfigValue("warpcommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Warp commands are disabled");
            return;
        }

        if (args.length <= 1){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        if (args.length > 2){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't have spaces in a warp name");
            return;
        }

        Coordinate playersCurrentCoordinate = new Coordinate();
        playersCurrentCoordinate.x = commandExecutor.getRegisteredX();
        playersCurrentCoordinate.y = commandExecutor.getRegisteredY();
        playersCurrentCoordinate.z = commandExecutor.getRegisteredZ();
        playersCurrentCoordinate.dimension = ServerMod.toEntityPlayerMP(commandExecutor).dimension;

        if (playersCurrentCoordinate.y < 1){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't set a warp below y=1");
            return;
        }


        String warpName = args[1];

        if (warpName.length() > 15){
            commandExecutor.displayChatMessage(ChatColors.RED + "Warp name too long, max length is 15");
            return;
        }

        String warpNameLowercase = warpName.toLowerCase();

        String allowedChars = "-'!abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < warpNameLowercase.length(); i++){
            if (allowedChars.indexOf(warpNameLowercase.charAt(i)) == -1){
                commandExecutor.displayChatMessage(ChatColors.RED + "Disallowed character(s), only a-z, A-Z and -!' allowed");
                return;
            }
        }

        if (warpName.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't set an empty warp name");
            commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
            return;
        }

        KivaServerUtils.warps.putIfAbsent(warpName, playersCurrentCoordinate);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Warp " + ChatColors.RESET + warpName + ChatColors.GREEN + " location set! [" + ChatColors.RESET + playersCurrentCoordinate.toStringXYZInt() + ChatColors.GREEN + "] in the " + ChatColors.RESET + Coordinate.dimensionToString(playersCurrentCoordinate.dimension));
    }
}
