package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.server.packets.Packet3Chat;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Mute extends CommandCompat {
    public Mute(){super("mute", true);}

    public String commandSyntax(){return "§e/mute <player>";}

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        String playerUsername = args[1];
        Boolean newMuted = KivaServerUtils.togglePlayerMuted(playerUsername);
        ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(KivaServerUtils.KSUBroadcastPrefix + playerUsername + " " + (newMuted ? ChatColors.RED + "is now" : ChatColors.GREEN + "is no longer") + " muted"));    }
}
