package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class MuteList extends CommandCompat {
    public MuteList(){super("mutelist", false);}

    public String commandSyntax(){return "§e/mutelist";}

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (KivaServerUtils.playersMuted.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "There are no muted players");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.BOLD + ChatColors.BLUE + "Players muted:" + ChatColors.RESET);

        for (String playerName : KivaServerUtils.playersMuted)
            commandExecutor.displayChatMessage(playerName);
    }
}
