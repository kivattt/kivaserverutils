package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.entity.player.EntityPlayerMP;

import java.util.ArrayList;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class TPA extends CommandCompat {
    public TPA(){super("tpa", false);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/tpa <player>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("tpacommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "TPA commands are disabled");
            return;
        }

        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        String targetName = args[1];

        if (targetName.equals(commandExecutor.getPlayerName())){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't teleport to yourself");
            return;
        }

        EntityPlayerMP targetPlayer = ServerMod.getGameInstance().configManager.getPlayerEntity(targetName);

        if (targetPlayer == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "Player " + ChatColors.RESET + targetName + ChatColors.RED + " not found");
            return;
        }

        KivaServerUtils.tpaRequests.putIfAbsent(targetName, new ArrayList<>());

        if (KivaServerUtils.tpaRequests.get(targetName).contains(commandExecutor.getPlayerName())){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "You've already sent a teleport request");
            return;
        }

        KivaServerUtils.tpaRequests.get(targetName).add(commandExecutor.getPlayerName());

        targetPlayer.displayChatMessage(ChatColors.GREEN + "New teleport request from " + ChatColors.RESET + commandExecutor.getPlayerName());
        targetPlayer.displayChatMessage(ChatColors.YELLOW + "/tpaccept or /tpdeny");

        commandExecutor.displayChatMessage(ChatColors.GREEN + "Sent teleport request to " + ChatColors.RESET + targetName);
    }
}
