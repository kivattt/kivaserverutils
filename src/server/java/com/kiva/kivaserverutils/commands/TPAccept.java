package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
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

        EntityPlayerMP me = ServerMod.toEntityPlayerMP(commandExecutor);

        /* /tpaccept - Accept all teleport requests */
        if (args.length == 1){
            boolean acceptSuccess = false;
            for (String playerName : tpaRequestsToMe)
                acceptSuccess |= acceptTPARequestFromPlayer(commandExecutor, playerName);

            if (!acceptSuccess)
                commandExecutor.displayChatMessage(ChatColors.RED + "No teleport requests to accept");

            KivaServerUtils.tpaRequests.get(commandExecutor.getPlayerName()).clear();
            return;
        }

        /* /tpaccept <player> - Accept teleport request from specific player */
        if (args.length == 2){
            String playerName = args[1];

            // We don't check for tpaccept to self since it should never be possible anyway

            if (!tpaRequestsToMe.contains(playerName)){
                commandExecutor.displayChatMessage(ChatColors.RED + "Player " + ChatColors.RESET + playerName + ChatColors.RED + " has not requested to be teleported");
                return;
            }

            if (!acceptTPARequestFromPlayer(commandExecutor, playerName)){
                commandExecutor.displayChatMessage(ChatColors.RED + "Player " + playerName + " not found");
                return;
            }

            tpaRequestsToMe.remove(playerName);
            return;
        }

        sendUsageMessage(commandSyntax(), commandExecutor);
    }

    public boolean acceptTPARequestFromPlayer(NetworkPlayer commandExecutor, final String username){
        EntityPlayerMP me = ServerMod.toEntityPlayerMP(commandExecutor);
        EntityPlayerMP player = ServerMod.getGameInstance().configManager.getPlayerEntity(username);
        if (player == null)
            return false;

        player.displayChatMessage(ChatColors.GREEN + "Teleport request to " + ChatColors.RESET + commandExecutor.getPlayerName() + ChatColors.GREEN + " was accepted");

        player.playerNetServerHandler.teleportTo(me.posX, me.posY, me.posZ, me.rotationYaw, me.rotationPitch);
        ServerMod.getGameInstance().configManager.sendChatMessageToAllOps(KivaServerUtils.KSUBroadcastPrefix + ChatColors.GREEN + "Teleporting " + ChatColors.RESET + username + ChatColors.GREEN + " to " + ChatColors.RESET + commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Teleport request from " + ChatColors.RESET + username + ChatColors.GREEN + " accepted");

        return true;
    }
}
