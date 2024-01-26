package com.kiva.kivaserverutils;

import com.fox2code.foxloader.network.NetworkPlayer;

public class UsageMessage {
    public static void sendUsageMessage(String commandSyntax, NetworkPlayer player) {
        sendUsageMessage(commandSyntax, player, true);
    }

    public static void sendUsageMessage(String commandSyntax, NetworkPlayer player, boolean startWithSyntaxText){
        // Could maybe make this some pop() thing with the right datatype to clean things up
        String[] syntaxSplitByNewlines = commandSyntax.split("\n");

        if (syntaxSplitByNewlines.length == 0)
            return;

        if (startWithSyntaxText)
            player.displayChatMessage("§aSyntax: " + syntaxSplitByNewlines[0]);
        else
            player.displayChatMessage(syntaxSplitByNewlines[0]);

        for (int i = 1; i < syntaxSplitByNewlines.length; i++)
            player.displayChatMessage(syntaxSplitByNewlines[i]);
    }
}
