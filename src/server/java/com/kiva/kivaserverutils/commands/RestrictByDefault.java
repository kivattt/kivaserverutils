package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.server.packets.Packet3Chat;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class RestrictByDefault extends CommandCompat {
    public RestrictByDefault(){super("restrictbydefault", true);}

    public String commandSyntax(){
        return ChatColors.YELLOW + "/restrictbydefault <true or false>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        if (!(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false"))){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        boolean value = args[1].equalsIgnoreCase("true");

        KivaServerUtils.config.put("restrictbydefault", value);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "restrictbydefault now set to: " + ChatColors.RESET + value);
        if (value) {
            ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(ChatColors.RED + "All players will now be in restrictive mode,"));
            ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(ChatColors.RED + "unless excluded with /restrictexclude"));
        }else {
            ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(ChatColors.YELLOW + "Players are now only in restrictive mode"));
            ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(ChatColors.YELLOW + "if present in /restrictlist"));
        }
    }
}
