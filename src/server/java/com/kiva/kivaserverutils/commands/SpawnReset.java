package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.KivaServerUtilsServer;
import net.minecraft.src.server.packets.Packet3Chat;

import static com.kiva.kivaserverutils.KivaServerUtils.spawnCommandLocation;

public class SpawnReset extends CommandCompat{
    public SpawnReset(){
        super("spawnreset", true);
    }

    public String commandSyntax(){
        return "§e/spawnreset";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        spawnCommandLocation = null;
        ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(KivaServerUtils.KSUBroadcastPrefix + ChatColors.YELLOW + "Spawn was reset (no /spawn now)"));
    }
}
