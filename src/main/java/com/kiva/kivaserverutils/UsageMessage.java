package com.kiva.kivaserverutils;

import com.fox2code.foxloader.network.NetworkPlayer;

import java.util.Arrays;

public class UsageMessage {
    public static void sendUsageMessage(String commandSyntax, NetworkPlayer player){
        // Could maybe make this some pop() thing with the right datatype to clean things up
        String[] syntaxSplitByNewlines = commandSyntax.split("\n");
        player.displayChatMessage("§aSyntax: " + syntaxSplitByNewlines[0]);

        for (int i = 1; i < syntaxSplitByNewlines.length; i++)
            player.displayChatMessage(syntaxSplitByNewlines[i]);
    }
}
