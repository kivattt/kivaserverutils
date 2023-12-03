package com.kiva.kivaserverutils;

import java.util.Map;

public class NicknameToUsername {
    public static String nicknameToUsername(final String nickname, final String selfToExclude){
        String caseInsensitiveMatch = null;

        for (Map.Entry<String, String> entry : KivaServerUtils.playerNicknames.entrySet()){
            // Prioritize a perfect case-sensitive match
            if (entry.getValue().equals(nickname)) {
                if (!entry.getKey().equalsIgnoreCase(selfToExclude)) // Ignore self
                    return entry.getKey();
            }

            if (entry.getValue().equalsIgnoreCase(nickname)) {
                if (!entry.getKey().equalsIgnoreCase(selfToExclude)) // Ignore self
                    caseInsensitiveMatch = entry.getKey();
            }
        }
        return caseInsensitiveMatch;
    }
}
