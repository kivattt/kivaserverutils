package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.ServerMod;
import net.minecraft.src.game.entity.player.EntityPlayerMP;

import java.util.Map;

public class NicknameAllowed {
    public static boolean nicknameIsAllowed(final String nickname, final String ownerToIgnore){
        for (Map.Entry<String, String> playerNick : KivaServerUtils.playerNicknames.entrySet()){
            // Ignore ourselves
            if (playerNick.getKey().equalsIgnoreCase(ownerToIgnore))
                continue;

            // Check if someone already has the nickname
            if (playerNick.getValue().equalsIgnoreCase(nickname))
                return false;

            // Check if nickname matches a username in playerNicknames
            // Not perfect, we'd have to store a null value for players with no nicknames to
            //  keep a username history, though this could better be achieved by parsing server.log
            //  but that's pretty hacky and would only fix this small issue where
            //  someone could set their nickname to a players username if that player
            // 1. Doesn't have a nickname
            // 2. Isn't currently on the server
            if (playerNick.getKey().equalsIgnoreCase(nickname))
                return false;
        }

        // Check if nickname matches any username of currently online players
        for (EntityPlayerMP p : ServerMod.getOnlinePlayers()){
            if (p.username.equalsIgnoreCase(ownerToIgnore))
                continue;

            if (p.username.equalsIgnoreCase(nickname))
                return false;
        }

        return true;
    }
}
