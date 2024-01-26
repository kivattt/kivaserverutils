package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class WarpDel extends CommandCompat{
    public WarpDel(){
        super("warpdel", true);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/warpdel <name>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (KivaServerUtils.getConfigValue("warpcommandsdisabled")){
            commandExecutor.displayChatMessage(ChatColors.RED + "Warp commands are disabled");
            return;
        }

        if (KivaServerUtils.warps == null || KivaServerUtils.warps.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no warps on this server");
            return;
        }

        if (args.length != 2){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        String warpName = args[1];

        if (!KivaServerUtils.warps.containsKey(warpName)){
            commandExecutor.displayChatMessage(ChatColors.RED + "There is no warp named " + ChatColors.RESET + warpName);
            return;
        }

        KivaServerUtils.warps.remove(warpName);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Deleted warp " + ChatColors.RESET + warpName);
    }
}
