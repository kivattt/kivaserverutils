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
        return ChatColors.YELLOW + "/home <optional name>";
    }

    // TODO Make DRY (Amount of times read and ignored: 1)
    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("homecommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Home commands are disabled");
            return;
        }

        if (KivaServerUtils.playerHomes == null || KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()) == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "You have no homes, use /sethome");
            return;
        }

        Coordinate home;
        String homeName = "";

        // Main home
        if (args.length <= 1) {
            home = KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).get("");
            if (home == null) {
                commandExecutor.displayChatMessage(ChatColors.RED + "You have no main home, use /sethome");
                return;
            }
        } else {
            if (args[1].isEmpty()){
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't use an empty home name");
                commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
                return;
            }

            home = KivaServerUtils.playerHomes.get(commandExecutor.getPlayerName()).get(args[1]);
            if (home == null) {
                commandExecutor.displayChatMessage(ChatColors.RED + "You have no home named \"" + args[1] + "\"");
                return;
            }
            homeName = args[1];
        }

        // Scrapped code attempting to fix the issue mention in the README.md Known Issues section
        // It seems getBlockId() doesn't work when the chunk isnt loaded, defeating the purpose entirely
        // Also Math.round() didnt seem to work out, maybe checking a 2x2 area would be necessary?
        // Would probably be a better solution to just pre-load the chunks somehow before the player reaches unloaded chunks
        /*int yWithOffset = (int)Math.round(homeCoordinate.y);

        // 0 = Overworld
        // I assume ServerMod.getGameInstance().worldMngr[0] is always the overworld, but this is to make sure
        for (WorldServer worldServer : ServerMod.getGameInstance().worldMngr){
            if (worldServer.dimension != 0)
                continue;

            for (; worldServer.getBlockId((int) Math.round(homeCoordinate.x), yWithOffset, (int)Math.round(homeCoordinate.z)) != Block.air.blockID; yWithOffset++){}
        }

        System.out.println("yOffset: " + yWithOffset);*/

        if (home.dimension != ServerMod.toEntityPlayerMP(commandExecutor).dimension)
            commandExecutor.sendPlayerThroughPortalRegistered();

        commandExecutor.teleportRegistered(home.x, home.y, home.z);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Teleported " + (homeName.isEmpty() ? "home" : "to " + homeName) + "!");
    }
}
