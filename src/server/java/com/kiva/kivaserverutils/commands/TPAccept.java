package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.entity.player.EntityPlayerMP;

import java.util.ArrayList;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class TPAccept extends CommandCompat {
    public TPAccept(){super("tpaccept", false);}

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/tpaccept <optional player>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("tpacommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "TPA commands are disabled");
            return;
        }

        ArrayList<String> tpaRequestsToMe = KivaServerUtils.tpaRequests.get(commandExecutor.getPlayerName());
        if (tpaRequestsToMe == null || tpaRequestsToMe.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "No teleport requests to accept");
            return;
        }

        /* /tpaccept - Accept all teleport requests */
        if (args.length == 1){
            if (tpaRequestsToMe.isEmpty()){
                commandExecutor.displayChatMessage(ChatColors.RED + "No teleport requests to accept");
                return;
            }

            for (String playerName : tpaRequestsToMe){
                if (acceptTPARequestFromPlayer(commandExecutor, playerName))
                    tpaRequestsToMe.remove(playerName);
            }

            return;
        }

        /* /tpaccept <player> - Accept teleport request from specific player */
        if (args.length == 2){
            EntityPlayerMP player = ServerMod.getGameInstance().configManager.getPlayerEntity(args[1]);

            if (player == null){
                commandExecutor.displayChatMessage(ChatColors.RED + "Player " + ChatColors.RESET + args[1] + ChatColors.RED + " not found");
                return;
            }

            // We don't check for tpaccept to self since it should never be possible anyway

            if (!tpaRequestsToMe.contains(player.username)){
                commandExecutor.displayChatMessage(ChatColors.RED + "Player " + ChatColors.RESET + player.username + ChatColors.RED + " has not requested to be teleported");
                return;
            }

            // I suppose this case might be possible if player disconnects at the right time, keeping it just in case
            if (!acceptTPARequestFromPlayer(commandExecutor, player.username))
                return;

            tpaRequestsToMe.remove(player.username);
            return;
        }

        sendUsageMessage(commandSyntax(), commandExecutor);
    }

    public boolean acceptTPARequestFromPlayer(NetworkPlayer commandExecutor, final String username){
        EntityPlayerMP me = ServerMod.toEntityPlayerMP(commandExecutor);
        EntityPlayerMP player = ServerMod.getGameInstance().configManager.getPlayerEntity(username);
        if (player == null) {
            commandExecutor.displayChatMessage(ChatColors.RED + "Player " + ChatColors.RESET + player.username + ChatColors.RED + " not found");
            return false;
        }

        if (me.dimension != player.dimension){
            commandExecutor.displayChatMessage(ChatColors.RED + "Player " + ChatColors.RESET + player.username + ChatColors.RED + " is in another dimension");
            return false;
        }

        // Theoretically possible with the player changing username
        if (me.username.equals(player.username)){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't teleport to yourself");
            return false;
        }

        player.displayChatMessage(ChatColors.GREEN + "Teleport request to " + ChatColors.RESET + commandExecutor.getPlayerName() + ChatColors.GREEN + " was accepted");

        player.playerNetServerHandler.teleportTo(me.posX, me.posY, me.posZ, me.rotationYaw, me.rotationPitch);
        ServerMod.getGameInstance().configManager.sendChatMessageToAllOps(KivaServerUtils.KSUBroadcastPrefix + ChatColors.GREEN + "Teleporting " + ChatColors.RESET + player.username + ChatColors.GREEN + " to " + ChatColors.RESET + commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Teleport request from " + ChatColors.RESET + player.username + ChatColors.GREEN + " accepted");

        return true;
    }
}
