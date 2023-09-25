package com.kiva.kivaserverutils;

import com.fox2code.foxloader.network.NetworkPlayer;

public class UsageMessage {
    public static void sendUsageMessage(String commandSyntax, NetworkPlayer player){
        player.displayChatMessage("§aSyntax: " + commandSyntax);
    }
}
