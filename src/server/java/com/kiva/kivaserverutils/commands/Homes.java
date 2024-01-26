package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;

import java.util.Map;

public class Homes extends CommandCompat{
    public Homes(){
        super("homes", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/homes <optional name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("homecommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Home commands are disabled");
            return;
        }

        if (KivaServerUtils.playerHomes == null
                || KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()) == null
                || KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).isEmpty()) {
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no homes, use /sethome");
            return;
        }

        if (args.length >= 2){
            if (args[1].isEmpty()){
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't use an empty home name");
                commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
                return;
            }

            Coordinate home = KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).get(args[1]);
            if (home == null){
                commandExecutor.displayChatMessage(ChatColors.RED + "You have no home named \"" + args[1] + "\"");
                return;
            }

            commandExecutor.displayChatMessage(ChatColors.GREEN + args[1] + ChatColors.RESET + " : " + home.toStringXYZInt() + " in the " + Coordinate.dimensionToString(home.dimension));
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.AQUA + "Your homes:");
        for (Map.Entry<String, Coordinate> home : KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).entrySet()){
            if (home.getKey().isEmpty()){ // Main home (no name)
                // TODO: Change "Main home" to something more appropriate and think about the colors of the home names
                commandExecutor.displayChatMessage("Main home : " + home.getValue().toStringXYZInt() + ChatColors.DARK_GRAY + " in the " + ChatColors.GRAY + Coordinate.dimensionToString(home.getValue().dimension));
                continue;
            }

            commandExecutor.displayChatMessage(ChatColors.GREEN + home.getKey() + ChatColors.RESET + " : " + home.getValue().toStringXYZInt() + ChatColors.DARK_GRAY + " in the " + ChatColors.GRAY + Coordinate.dimensionToString(home.getValue().dimension));
        }
    }
}
