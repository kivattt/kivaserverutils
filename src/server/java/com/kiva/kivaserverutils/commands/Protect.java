package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.ProtectedRegion;
import net.minecraft.src.game.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Protect extends CommandCompat {
    public Protect(){
        super("protect", true, false, new String[]{"pr"});
    }

    public static String getArgumentsForAction(final String action){
        if (action.equals("list"))
            return action;

        if (action.equals("removeall"))
            return action + " <confirmation>";

        if (action.equals("rename"))
            return action + " <name> <new name>";

        return action + " <name>";
    }

    public static String getUsageMsgForAction(final String commandNameUsed, final String action){
        return ChatColors.RED + "Usage: " + commandNameUsed.toLowerCase() + " " + getArgumentsForAction(action);
    }

    public static List<String> getActionList(){
        return Arrays.asList("list", "set", "remove", "removeall", "rename", "expandheight", "info");
    }

    public void sendHelpMsg(final String commandNameUsed, final NetworkPlayer commandExecutor){
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Region protection commands:");
        for (String action : getActionList())
            commandExecutor.displayChatMessage(ChatColors.YELLOW + commandNameUsed.toLowerCase() + " " + getArgumentsForAction(action));
    }

    public boolean hasDisallowedCharacters(final String regionName){
        String regionNameLowercase = regionName.toLowerCase();
        String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789_-";

        for (int i = 0; i < regionNameLowercase.length(); i++){
            if (allowedChars.indexOf(regionNameLowercase.charAt(i)) == -1)
                return true;
        }

        return false;
    }

    public boolean regionNameTooLong(final String regionName){
        return regionName.length() > 24;
    }

    public void sendDisallowedCharactersWarning(final NetworkPlayer commandExecutor, final String inWhat){
        commandExecutor.displayChatMessage(ChatColors.RED + "Disallowed character(s) in " + inWhat + ", only");
        commandExecutor.displayChatMessage(ChatColors.RED + "a-z (case-insensitive), 0-9 and symbols -_ allowed");
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor) {
        if (args.length <= 1) {
            sendHelpMsg(args[0], commandExecutor);
            return;
        }

        for (String arg : args) {
            if (arg.isEmpty()) {
                commandExecutor.displayChatMessage(ChatColors.RED + "You can't specify an empty argument");
                commandExecutor.displayChatMessage(ChatColors.RED + "(You probably typed 2 spaces in the command)");
                return;
            }
        }

        final String action = args[1].toLowerCase();

        if (!getActionList().contains(action)){
            sendHelpMsg(args[0], commandExecutor);
            return;
        }

        if (action.equals("list")) {
            if (KivaServerUtils.protectedRegions.isEmpty()){
                commandExecutor.displayChatMessage(ChatColors.RED + "There are no protected regions");
                return;
            }

            // TODO: Output in alphabetical order
            commandExecutor.displayChatMessage(ChatColors.DARK_GREEN + "Protected regions:");
            for (Map.Entry<String, ProtectedRegion> protectedRegion : KivaServerUtils.protectedRegions.entrySet())
                commandExecutor.displayChatMessage(ChatColors.BLUE + protectedRegion.getKey() + ChatColors.RESET + " : " + protectedRegion.getValue());

            return;
        }

        // The name argument is required for the actions below
        String name = "";
        try {
            name = args[2];
        } catch(ArrayIndexOutOfBoundsException e){
            if (!action.equals("removeall")) {
                commandExecutor.displayChatMessage(getUsageMsgForAction(args[0], action));
                return;
            }
        }

        switch (action) {
            case "info":
                ProtectedRegion protectedRegion = KivaServerUtils.protectedRegions.get(name);
                if (protectedRegion == null) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "There is no protected region named " + ChatColors.RESET + name);
                    return;
                }

                commandExecutor.displayChatMessage(ChatColors.BLUE + name + ChatColors.RESET + " : " + protectedRegion);
                break;
            case "set":
                NetworkPlayer.NetworkPlayerController netPlayerController = commandExecutor.getNetworkPlayerController();
                if (!netPlayerController.hasSelection()) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "No region selected!");
                    commandExecutor.displayChatMessage(ChatColors.RED + "Mark the corners of the area with");
                    commandExecutor.displayChatMessage(ChatColors.RED + "a wooden axe in creative mode as OP");
                    return;
                }

                if (regionNameTooLong(name)) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "Region name too long, max length is 24");
                    return;
                }

                if (hasDisallowedCharacters(name)) {
                    sendDisallowedCharactersWarning(commandExecutor, "name");
                    return;
                }

                ProtectedRegion newProtectedRegion = new ProtectedRegion();
                newProtectedRegion.xMin = netPlayerController.getMinX();
                newProtectedRegion.yMin = netPlayerController.getMinY();
                newProtectedRegion.zMin = netPlayerController.getMinZ();

                newProtectedRegion.xMax = netPlayerController.getMaxX();
                newProtectedRegion.yMax = netPlayerController.getMaxY();
                newProtectedRegion.zMax = netPlayerController.getMaxZ();

                newProtectedRegion.dimension = ((EntityPlayerMP) commandExecutor).dimension;

                if (KivaServerUtils.protectedRegions.containsKey(name))
                    commandExecutor.displayChatMessage(ChatColors.YELLOW + "Protected region " + ChatColors.RESET + name + ChatColors.YELLOW + " already exists, overwriting:");
                else
                    commandExecutor.displayChatMessage(ChatColors.GREEN + "New protected region " + ChatColors.RESET + name + ChatColors.GREEN + ":");
                commandExecutor.displayChatMessage(newProtectedRegion.toString());
                KivaServerUtils.protectedRegions.put(name, newProtectedRegion);
                break;
            case "remove":
                if (!KivaServerUtils.protectedRegions.containsKey(name)) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "There is no protected region named " + ChatColors.RESET + name);
                    return;
                }

                KivaServerUtils.protectedRegions.remove(name);
                commandExecutor.displayChatMessage(ChatColors.GREEN + "Removed protected region " + ChatColors.RESET + name);
                break;
            case "removeall":
                if (KivaServerUtils.protectedRegions.isEmpty()) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "There are no protected regions to remove!");
                    return;
                }

                if (!name.equalsIgnoreCase("yes")) {
                    commandExecutor.displayChatMessage(ChatColors.DARK_RED + "This command will delete all region protections!");
                    commandExecutor.displayChatMessage(ChatColors.YELLOW + "Type " + ChatColors.GREEN + args[0] + " removeall yes " + ChatColors.YELLOW + "to confirm");
                    return;
                }

                KivaServerUtils.protectedRegions.clear();
                commandExecutor.displayChatMessage(ChatColors.GREEN + "All protected regions removed");
                break;
            case "rename":
                String newName;
                try {
                    newName = args[3];
                } catch (ArrayIndexOutOfBoundsException e) {
                    commandExecutor.displayChatMessage(getUsageMsgForAction(args[0], action));
                    return;
                }

                if (regionNameTooLong(newName)) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "New region name too long, max length is 24");
                    return;
                }

                if (hasDisallowedCharacters(newName)) {
                    sendDisallowedCharactersWarning(commandExecutor, "new name");
                    return;
                }

                if (!KivaServerUtils.protectedRegions.containsKey(name)) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "There is no protected region named " + ChatColors.RESET + name);
                    return;
                }

                if (name.equals(newName)) {
                    commandExecutor.displayChatMessage(ChatColors.YELLOW + "Name unchanged");
                    return;
                }

                if (KivaServerUtils.protectedRegions.containsKey(newName)) {
                    commandExecutor.displayChatMessage(ChatColors.YELLOW + "That name already exists, nothing done");
                    return;
                }

                // We can assume this won't be null given the above .containsKey
                ProtectedRegion protectedRegionToMove = KivaServerUtils.protectedRegions.get(name);
                KivaServerUtils.protectedRegions.put(newName, protectedRegionToMove);
                KivaServerUtils.protectedRegions.remove(name); // In this order to make sure the region is never removed

                commandExecutor.displayChatMessage(ChatColors.GREEN + "Protected region " + ChatColors.RESET + name + ChatColors.GREEN + " renamed: " + ChatColors.RESET + newName);
                break;
            case "expandheight":
                if (!KivaServerUtils.protectedRegions.containsKey(name)) {
                    commandExecutor.displayChatMessage(ChatColors.RED + "There is no protected region named " + ChatColors.RESET + name);
                    return;
                }

                KivaServerUtils.protectedRegions.get(name).yMin = ServerMod.getGameInstance().getWorldManager(((EntityPlayerMP) commandExecutor).dimension).lowestY;
                KivaServerUtils.protectedRegions.get(name).yMax = ServerMod.getGameInstance().getWorldManager(((EntityPlayerMP) commandExecutor).dimension).highestY - 1;

                commandExecutor.displayChatMessage(ChatColors.GREEN + "Protected region " + name + " now spans all height");
                break;
        }
    }
}
