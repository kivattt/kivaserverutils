package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.server.packets.Packet3Chat;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class MaxHomes extends CommandCompat{
    public MaxHomes(){
        super("maxhomes", true);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/maxhomes <number>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2) {
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Max amount of homes per player is currently " + ChatColors.RESET + KivaServerUtils.maxHomesPerPlayer);
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        Integer newMaxHomesPerPlayer;
        try {
            newMaxHomesPerPlayer = Integer.parseInt(args[1]);
        } catch (NumberFormatException e){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        if (newMaxHomesPerPlayer <= 0){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't set the max amount of homes per player to " + newMaxHomesPerPlayer);
            commandExecutor.displayChatMessage(ChatColors.RED + "Use " + ChatColors.YELLOW + "/homecommandsdisabled true" + ChatColors.RED + " to disable homes");
            return;
        }

        if (newMaxHomesPerPlayer.equals(KivaServerUtils.maxHomesPerPlayer)){
            commandExecutor.displayChatMessage(ChatColors.YELLOW + "The max amount of homes per player is already " + ChatColors.RESET + newMaxHomesPerPlayer);
            return;
        }

        KivaServerUtils.maxHomesPerPlayer = newMaxHomesPerPlayer;
        ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(KivaServerUtils.KSUBroadcastPrefix + ChatColors.GREEN + "Max amount of homes per player is now " + ChatColors.RESET + KivaServerUtils.maxHomesPerPlayer));
    }
}
