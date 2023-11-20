package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Map;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class TPARevoke extends CommandCompat {
    public TPARevoke(){super("tparevoke", false, false, new String[]{"tprevoke"});}

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/tparevoke";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (KivaServerUtils.getConfigValue("tpacommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "TPA commands are disabled");
            return;
        }

        if (args.length == 1){
            boolean revokedAny = false;

            for (Map.Entry<String, ArrayList<String>> entry : KivaServerUtils.tpaRequests.entrySet()){
                if (entry.getValue().contains(commandExecutor.getPlayerName())) {
                    EntityPlayerMP targetPlayer = ServerMod.getGameInstance().configManager.getPlayerEntity(entry.getKey());
                    if (targetPlayer != null)
                        targetPlayer.displayChatMessage(commandExecutor.getPlayerName() + ChatColors.RED + " revoked their teleport request");

                    commandExecutor.displayChatMessage(ChatColors.RED + "Revoked teleport request to " + ChatColors.RESET + entry.getKey());

                    entry.getValue().remove(commandExecutor.getPlayerName());
                    revokedAny = true;
                }
            }

            if (!revokedAny){
                commandExecutor.displayChatMessage(ChatColors.RED + "No teleport requests to revoke");
                return;
            }

            return;
        }

        sendUsageMessage(commandSyntax(), commandExecutor);
    }
}
