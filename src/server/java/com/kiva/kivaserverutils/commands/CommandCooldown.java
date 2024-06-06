package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.fox2code.ChatColors;
import net.minecraft.src.server.packets.Packet3Chat;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class CommandCooldown extends CommandCompat {
    public CommandCooldown() {
        super("commandcooldown", true);
    }

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/commandcooldown <seconds>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (args.length != 2) {
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Command cooldown is currently " + ChatColors.RESET + KivaServerUtils.commandCooldownSeconds);
            if (KivaServerUtils.commandCooldownSeconds == 0d)
                commandExecutor.displayChatMessage(ChatColors.YELLOW + "(No cooldown)");
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        double newCommandCooldown;
        try {
            newCommandCooldown = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        if (newCommandCooldown < 0d) newCommandCooldown = 0d;
        KivaServerUtils.commandCooldownSeconds = newCommandCooldown;
        ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(KivaServerUtils.KSUBroadcastPrefix + ChatColors.GREEN + "Command cooldown is now " + ChatColors.RESET + KivaServerUtils.commandCooldownSeconds));
    }
}
