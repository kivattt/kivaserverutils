package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.entity.player.EntityPlayerMP;

import java.util.ArrayList;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class TPDeny extends CommandCompat {
    public TPDeny(){super("tpdeny", false);}

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/tpdeny <optional player>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("tpacommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "TPA commands are disabled");
            return;
        }

        ArrayList<String> tpaRequestsToMe = KivaServerUtils.tpaRequests.get(commandExecutor.getPlayerName());
        if (tpaRequestsToMe == null || tpaRequestsToMe.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "No teleport requests to deny");
            return;
        }

        /* /tpdeny - Deny all teleport requests */
        if (args.length == 1){
            boolean denySuccess = false;
            for (String playerName : tpaRequestsToMe)
                denySuccess |= denyTPARequestFromPlayer(commandExecutor, playerName);

            if (!denySuccess)
                commandExecutor.displayChatMessage(ChatColors.RED + "No teleport requests to deny");

            KivaServerUtils.tpaRequests.get(commandExecutor.getPlayerName()).clear();
            return;
        }

        /* /tpdeny <player> - Deny teleport request from specific player */
        if (args.length == 2){
            String playerName = args[1];

            // We don't check for tpdeny to self since it should never be possible anyway

            if (!tpaRequestsToMe.contains(playerName)){
                commandExecutor.displayChatMessage(ChatColors.RED + "Player " + ChatColors.RESET + playerName + ChatColors.RED + " has not requested to be teleported");
                return;
            }

            if (!denyTPARequestFromPlayer(commandExecutor, playerName)){
                commandExecutor.displayChatMessage(ChatColors.RED + "Player " + playerName + " not found");
                return;
            }

            tpaRequestsToMe.remove(playerName);
            return;
        }

        sendUsageMessage(commandSyntax(), commandExecutor);
    }

    public boolean denyTPARequestFromPlayer(NetworkPlayer commandExecutor, final String username){
        EntityPlayerMP player = ServerMod.getGameInstance().configManager.getPlayerEntity(username);
        if (player == null)
            return false;

        player.displayChatMessage(ChatColors.RED + "Teleport request to " + ChatColors.RESET + commandExecutor.getPlayerName() + ChatColors.RED + " was denied");
        commandExecutor.displayChatMessage(ChatColors.RED + "Teleport request from " + ChatColors.RESET + username + ChatColors.RED + " denied");

        return true;
    }
}
