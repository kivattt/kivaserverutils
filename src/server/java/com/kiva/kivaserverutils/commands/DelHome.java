package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

public class DelHome extends CommandCompat{
    public DelHome(){
        super("delhome", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/delhome <optional name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.getConfigValue("homecommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Home commands are disabled");
            return;
        }

        if (KivaServerUtils.playerHomes == null || KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()) == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no homes, use /sethome");
            return;
        }

        String homeName = "";
        if (args.length >= 2) {
            if (args[1].isEmpty()) {
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't delete an empty home name");
                commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
                return;
            }
            homeName = args[1];
        }

        Coordinate home = KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).get(homeName);

        if (home == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no " + (homeName.isEmpty() ? "main home" : "home named \"" + homeName + "\""));
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.RED + (homeName.isEmpty() ? "Main home" : "Home \"" + homeName + "\"") + " deleted");
        commandExecutor.displayChatMessage(ChatColors.YELLOW + "It was located at " + ChatColors.GREEN + "[" + ChatColors.YELLOW + home.toStringXYZInt() + ChatColors.GREEN + "]" + ChatColors.YELLOW + " in the " + Coordinate.dimensionToString(home.dimension));
        KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).remove(homeName);
    }
}
