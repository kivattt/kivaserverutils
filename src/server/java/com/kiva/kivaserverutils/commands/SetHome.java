package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.HashMap;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class SetHome extends CommandCompat{
    public SetHome(){
        super("sethome", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/sethome <optional name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.getConfigValue("homecommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Home commands are disabled");
            return;
        }

        if (args.length > 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't have spaces in your home name");
            return;
        }

        if (KivaServerUtils.playerHomes != null && KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()) != null){
            if (KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).size() >= KivaServerUtils.maxHomesPerPlayer){
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't have more than " + ChatColors.RESET + KivaServerUtils.maxHomesPerPlayer + ChatColors.RED + " home" + (KivaServerUtils.maxHomesPerPlayer > 1 ? "s" : "") + " on this server");
                return;
            }
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

        // Main home
        if (args.length == 1) {
            KivaServerUtils.playerHomes.putIfAbsent(commandExecutor.getPlayerName(), new HashMap<>());
            KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).put("", playersCurrentCoordinate);
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Home location set! [" + ChatColors.RESET + playersCurrentCoordinate.toStringXYZInt() + ChatColors.GREEN + "] in the " + ChatColors.RESET + Coordinate.dimensionToString(playersCurrentCoordinate.dimension));
            return;
        }

        if (args.length >= 2) {
            String homeName = args[1];

            if (homeName.length() > 15){
                commandExecutor.displayChatMessage(ChatColors.RED + "Home name too long, max length is 15");
                return;
            }

            String homeNameLowercase = homeName.toLowerCase();

            String allowedChars = "-'!abcdefghijklmnopqrstuvwxyz";
            for (int i = 0; i < homeNameLowercase.length(); i++){
                if (allowedChars.indexOf(homeNameLowercase.charAt(i)) == -1){
                    commandExecutor.displayChatMessage(ChatColors.RED + "Disallowed character(s), only a-z, A-Z and -!' allowed");
                    return;
                }
            }

            if (homeName.isEmpty()){
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't set an empty home name");
                commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
                return;
            }

            KivaServerUtils.playerHomes.putIfAbsent(commandExecutor.getPlayerName(), new HashMap<>());
            KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).put(homeName, playersCurrentCoordinate);
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Home " + ChatColors.RESET + homeName + ChatColors.GREEN + " location set! [" + ChatColors.RESET + playersCurrentCoordinate.toStringXYZInt() + ChatColors.GREEN + "] in the " + ChatColors.RESET + Coordinate.dimensionToString(playersCurrentCoordinate.dimension));
        }
    }
}
