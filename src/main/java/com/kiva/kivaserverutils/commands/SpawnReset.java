package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;

import static com.kiva.kivaserverutils.KivaServerUtils.spawnCommandLocation;
import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class SpawnReset extends CommandCompat{
    public SpawnReset(){
        super("spawnreset", true);
    }

    public String commandSyntax(){
        return "§e/spawnreset";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        spawnCommandLocation = null;
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Spawn reset (No spawn now)");
    }
}
