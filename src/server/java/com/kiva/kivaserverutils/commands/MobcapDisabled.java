package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.server.packets.Packet3Chat;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class MobcapDisabled extends CommandCompat{
    public MobcapDisabled(){
        super("mobcapdisabled", true);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/mobcapdisabled <true or false>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        if (!(args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false"))){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        boolean value = args[1].equalsIgnoreCase("true");

        KivaServerUtils.config.put("mobcapdisabled", value);
        //commandExecutor.displayChatMessage(ChatColors.GREEN + "mobcapdisabled now set to: " + ChatColors.RESET + value);
        ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(KivaServerUtils.KSUBroadcastPrefix + (value ? ChatColors.RED + "Mob cap disabled" : ChatColors.GREEN + "Mob cap enabled")));
    }
}
