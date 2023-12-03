package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.WorldServer;

public class Home extends CommandCompat{
    public Home(){
        super("home", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/home";
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

        if (homeCoordinate.dimension != ServerMod.toEntityPlayerMP(commandExecutor).dimension)
            commandExecutor.sendPlayerThroughPortalRegistered();

        // Scrapped code attempting to fix the issue mention in the README.md Known Issues
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

        commandExecutor.teleportRegistered(homeCoordinate.x, homeCoordinate.y, homeCoordinate.z);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Teleported to home!");
    }
}
