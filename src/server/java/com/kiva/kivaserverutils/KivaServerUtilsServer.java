package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.fox2code.foxloader.registry.RegisteredItemStack;
import com.kiva.kivaserverutils.commands.*;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.item.ItemFood;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KivaServerUtilsServer extends KivaServerUtils implements ServerMod{
    public static final String KSUBasePath = "mods/KivaServerUtils/";
    public static final String playerNicknamesFilename                = KSUBasePath + "playernicknames.properties";
    public static final String playerFlagsFilename                    = KSUBasePath + "playerflags.properties";
    public static final String playerPronounsFilename                 = KSUBasePath + "playerpronouns.properties";
    public static final String playerNameColorsFilename               = KSUBasePath + "playernamecolors.properties";
    public static final String playerPronounColorsFilename            = KSUBasePath + "playerpronouncolors.properties";
    public static final String playerHomesFilename                    = KSUBasePath + "playerhomes.txt";
    public static final String maxHomesPerPlayerFilename              = KSUBasePath + "maxhomesperplayer.txt";
    public static final String playersInRestrictiveModeFilename       = KSUBasePath + "restrictivemodeplayers.txt";
    public static final String playersExcludedRestrictiveModeFilename = KSUBasePath + "restrictivemodeplayersexcluded.txt";
    public static final String playersMutedFilename                   = KSUBasePath + "playersmuted.txt";
    public static final String protectedRegionsFilename               = KSUBasePath + "protectedregions.txt";
    public static final String spawnCommandLocationFilename           = KSUBasePath + "spawncommandlocation.txt";
    public static final String warpsLocationFilename                  = KSUBasePath + "warps.txt";
    public static final String configFilename                         = KSUBasePath + "config.txt";

    public static boolean possibleFirstRun = false;

    public void onInit(){
        loadAllConfigs();

        // Stupid, I know. This just makes sure /kivashowconfig shows all available config flags
        KivaServerUtils.config.putIfAbsent("mobcapdisabled", false);
        KivaServerUtils.config.putIfAbsent("explosionsbreakchests", false);
        KivaServerUtils.config.putIfAbsent("homecommandsdisabled", false);
        KivaServerUtils.config.putIfAbsent("warpcommandsdisabled", false);
        KivaServerUtils.config.putIfAbsent("tpacommandsdisabled", false);
        KivaServerUtils.config.putIfAbsent("falldamagedisabled", false);
        KivaServerUtils.config.putIfAbsent("restrictbydefault", false);

        if (possibleFirstRun) {
            System.out.println("+----------------------------------------------------------------------+");
            System.out.println("|                                                                      |");
            System.err.println("|        HEADS UP!!!        [KivaServerUtils]        HEADS UP!!!       |");
            System.out.println("| If you see a bunch of errors above, like \"Failed to load ...\", it is |");
            System.out.println("| perfectly normal for a first-time run, or update of KivaServerUtils. |");
            System.out.println("|                           (Just ignore it)                           |");
            System.out.println("|                                  :3                                  |");
            System.out.println("+----------------------------------------------------------------------+");
        }

        nameColorChoicesNames = getNameAndPronounColorChoiceNames();
        flagColorChoicesNames = getFlagColorChoiceNames();
        pronounColorChoicesNames = getNameAndPronounColorChoiceNames();

        CommandCompat.registerCommand(new Nick());
        CommandCompat.registerCommand(new NameOf());
        CommandCompat.registerCommand(new NickList());
        CommandCompat.registerCommand(new NickListAll());
        CommandCompat.registerCommand(new NickSet());
        CommandCompat.registerCommand(new NickReset());

        CommandCompat.registerCommand(new NameColor());
        CommandCompat.registerCommand(new NameColorReset());

        CommandCompat.registerCommand(new Flag());
        CommandCompat.registerCommand(new FlagReset());

        CommandCompat.registerCommand(new Pronouns());
        CommandCompat.registerCommand(new PronounsList());
        CommandCompat.registerCommand(new PronounsListAll());
        CommandCompat.registerCommand(new PronounsSet());
        CommandCompat.registerCommand(new PronounsReset());
        CommandCompat.registerCommand(new PronounsColor());
        CommandCompat.registerCommand(new PronounsColorReset());

        CommandCompat.registerCommand(new Spawn());
        CommandCompat.registerCommand(new SpawnWhere());
        CommandCompat.registerCommand(new SpawnSet());
        CommandCompat.registerCommand(new SpawnReset());

        CommandCompat.registerCommand(new Home());
        CommandCompat.registerCommand(new Homes());
        CommandCompat.registerCommand(new SetHome());
        CommandCompat.registerCommand(new DelHome());
        CommandCompat.registerCommand(new RenameHome());
        CommandCompat.registerCommand(new MaxHomes());

        CommandCompat.registerCommand(new Mute());
        CommandCompat.registerCommand(new MuteList());
        CommandCompat.registerCommand(new MuteListAll());

        CommandCompat.registerCommand(new Restrict());
        CommandCompat.registerCommand(new RestrictList());
        CommandCompat.registerCommand(new RestrictListAll());

        CommandCompat.registerCommand(new RestrictExclude());
        CommandCompat.registerCommand(new RestrictExcludeList());
        CommandCompat.registerCommand(new RestrictExcludeListAll());

        CommandCompat.registerCommand(new RestrictByDefault());

        CommandCompat.registerCommand(new KivaShowConfig());
        CommandCompat.registerCommand(new ExplosionsBreakChests());
        CommandCompat.registerCommand(new HomeCommandsDisabled());
        CommandCompat.registerCommand(new MobcapDisabled());

        CommandCompat.registerCommand(new Teleport());
        CommandCompat.registerCommand(new KivaVersion());

        CommandCompat.registerCommand(new Protect());

        CommandCompat.registerCommand(new TPA());
        CommandCompat.registerCommand(new TPARevoke());
        CommandCompat.registerCommand(new TPAccept());
        CommandCompat.registerCommand(new TPDeny());
        CommandCompat.registerCommand(new TPACommandsDisabled());

        CommandCompat.registerCommand(new FallDamageDisabled());

        CommandCompat.registerCommand(new Warp());
        CommandCompat.registerCommand(new Warps());
        CommandCompat.registerCommand(new WarpSet());
        CommandCompat.registerCommand(new WarpDel());
        CommandCompat.registerCommand(new WarpRename());
        CommandCompat.registerCommand(new WarpCommandsDisabled());

        CommandCompat.registerCommand(new AFK());

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable periodicWriteAllConfigs = this::writeAllConfigs;
        executor.scheduleAtFixedRate(periodicWriteAllConfigs, 1, 1, TimeUnit.MINUTES);

        System.out.println("KivaServerUtils initialized");
    }

    // Let's revoke all tpa requests from the player on disconnect
    @Override
    public boolean onNetworkPlayerDisconnected(NetworkPlayer networkPlayer, String kickMessage, boolean cancelled) {
        for (Map.Entry<String, ArrayList<String>> entry : KivaServerUtils.tpaRequests.entrySet())
            entry.getValue().remove(networkPlayer.getPlayerName());
        return false;
    }

    @Override
    public void onServerStop(NetworkPlayer.ConnectionType connectionType) {
        super.onServerStop(connectionType);

        writeAllConfigs();
    }

    public void loadAllConfigs() {
        KivaServerUtils.playerNicknames                    = FileWriteAndLoadHashmap.loadHashmapFromFile(playerNicknamesFilename);
        KivaServerUtils.playerFlags                        = FileWriteAndLoadHashmap.loadHashmapFromFile(playerFlagsFilename);
        KivaServerUtils.playerPronouns                     = FileWriteAndLoadHashmap.loadHashmapFromFile(playerPronounsFilename);
        KivaServerUtils.playerNameColors                   = FileWriteAndLoadHashmap.loadHashmapFromFile(playerNameColorsFilename);
        KivaServerUtils.playerPronounColors                = FileWriteAndLoadHashmap.loadHashmapFromFile(playerPronounColorsFilename);
        KivaServerUtils.playerHomes                        = FileWriteAndLoadPlayerHomes.loadPlayerHomesFromFile(playerHomesFilename);
        KivaServerUtils.maxHomesPerPlayer                  = FileWriteAndLoadInteger.loadIntegerFromFile(maxHomesPerPlayerFilename);
        if (KivaServerUtils.maxHomesPerPlayer == null) KivaServerUtils.maxHomesPerPlayer = 10; // Default value
        KivaServerUtils.playersInRestrictiveMode           = FileWriteAndLoadStringSet.loadStringSetFromFile(playersInRestrictiveModeFilename);
        KivaServerUtils.playersExcludedFromRestrictiveMode = FileWriteAndLoadStringSet.loadStringSetFromFile(playersExcludedRestrictiveModeFilename);
        KivaServerUtils.playersMuted                       = FileWriteAndLoadStringSet.loadStringSetFromFile(playersMutedFilename);
        KivaServerUtils.protectedRegions                   = FileWriteAndLoadStringProtectedRegionHashmap.loadStringProtectedRegionHashmapFromFile(protectedRegionsFilename);
        KivaServerUtils.spawnCommandLocation               = FileWriteAndLoadSpawn.loadSpawnFromFile(spawnCommandLocationFilename);
        KivaServerUtils.warps                              = FileWriteAndLoadWarps.loadWarpsFromFile(warpsLocationFilename);
        KivaServerUtils.config                             = FileWriteAndLoadStringBooleanHashmap.loadStringBooleanHashmapFromFile(configFilename);
    }

    public void writeAllConfigs() {
        // Make sure mods/KivaServerUtils directory exists
        Path modsPath = Paths.get(KSUBasePath);
        try {
            Files.createDirectory(modsPath);
        } catch (IOException e){
            if (!(e instanceof FileAlreadyExistsException)) {
                System.err.println(KSUBasePath + " was not found, unable to store mod data");
                e.printStackTrace();
                return;
            }
        }

        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerNicknames, playerNicknamesFilename);
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerFlags, playerFlagsFilename);
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerPronouns, playerPronounsFilename);
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerNameColors, playerNameColorsFilename);
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerPronounColors, playerPronounColorsFilename);
        FileWriteAndLoadPlayerHomes.writePlayerHomesToFile(KivaServerUtils.playerHomes, playerHomesFilename);
        FileWriteAndLoadInteger.writeIntegerToFile(KivaServerUtils.maxHomesPerPlayer, maxHomesPerPlayerFilename);
        FileWriteAndLoadStringSet.writeStringSetToFile(KivaServerUtils.playersInRestrictiveMode, playersInRestrictiveModeFilename);
        FileWriteAndLoadStringSet.writeStringSetToFile(KivaServerUtils.playersExcludedFromRestrictiveMode, playersExcludedRestrictiveModeFilename);
        FileWriteAndLoadStringSet.writeStringSetToFile(KivaServerUtils.playersMuted, playersMutedFilename);
        FileWriteAndLoadStringProtectedRegionHashmap.writeStringProtectedRegionHashmapToFile(KivaServerUtils.protectedRegions, protectedRegionsFilename);
        FileWriteAndLoadSpawn.writeSpawnToFile(KivaServerUtils.spawnCommandLocation, spawnCommandLocationFilename);
        FileWriteAndLoadWarps.writeWarpsToFile(KivaServerUtils.warps, warpsLocationFilename);
        FileWriteAndLoadStringBooleanHashmap.writeStringBooleanHashmapToFile(KivaServerUtils.config, configFilename);
    }

    public boolean handleRestrictiveModeAndRegionProtection(NetworkPlayer networkPlayer, final int x, final int y, final int z){
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z, true);
    }
    public boolean handleRestrictiveModeAndRegionProtection(NetworkPlayer networkPlayer, final int x, final int y, final int z, boolean sendNotifyMsg){
        boolean isRestrictiveMode = KivaServerUtils.isPlayerInRestrictiveMode(networkPlayer.getPlayerName());
        Pair<String, Boolean> protectedRegion = KivaServerUtils.inProtectedRegion(x, y, z, ((EntityPlayerMP)networkPlayer).dimension);

        if (sendNotifyMsg) {
            if (isRestrictiveMode)
                networkPlayer.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);
            else if (protectedRegion.second)
                networkPlayer.displayChatMessage(KivaServerUtils.notifyProtectedRegion + protectedRegion.first);
        }

        return isRestrictiveMode | protectedRegion.second;
    }

    // Prevents breaking blocks while in restrictive mode
    @Override
    public boolean onPlayerStartBreakBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, boolean cancelled) {
        // We don't output notify message while in survival, would be too much output in chat with it
        // Has to be enabled for creative mode, since this event actually cancels properly in creative mode
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z, ((EntityPlayerMP)networkPlayer).gamemode != 0);
    }

    @Override
    public boolean onPlayerBreakBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, boolean cancelled) {
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z);
    }

    // Prevents placing blocks while in restrictive mode
    @Override
    public boolean onPlayerUseItemOnBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, float xOffset, float yOffset, float zOffset, boolean cancelled) {
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z);
    }

    // Prevents using items like buckets to place water/lava when in restrictive mode
    @Override
    public boolean onPlayerUseItem(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, boolean cancelled) {
        // Still allow players in restrictive mode to eat food
        if (itemStack.getRegisteredItem() instanceof ItemFood)
            return false;

        boolean isRestrictiveMode = KivaServerUtilsServer.isPlayerInRestrictiveMode(networkPlayer.getPlayerName());

        if (isRestrictiveMode)
            networkPlayer.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);

        return isRestrictiveMode;
    }

    public LinkedHashMap<String, String> getNameAndPronounColorChoiceNames(){
        LinkedHashMap<String, String> ret = new LinkedHashMap<>();

        ret.put("white", ChatColors.WHITE);
        ret.put("gray", ChatColors.GRAY);
        ret.put("darkgray", ChatColors.DARK_GRAY);
        ret.put("black", ChatColors.BLACK);

        ret.put("yellow", ChatColors.YELLOW);
        ret.put("orange", ChatColors.GOLD); // "gold" is unhinged, let's make it orange

        ret.put("green", ChatColors.GREEN);
        ret.put("darkgreen", ChatColors.DARK_GREEN);

        ret.put("lightblue", ChatColors.AQUA); // Default name color
        ret.put("cyan", ChatColors.DARK_AQUA);
        ret.put("blue", ChatColors.BLUE);
        ret.put("darkblue", ChatColors.DARK_BLUE);

        ret.put("pink", ChatColors.LIGHT_PURPLE);
        ret.put("purple", ChatColors.DARK_PURPLE);
        ret.put("red", ChatColors.DARK_RED);

        //ret.put("red", ChatColors.RED); // Reserved for operators as default color for names, disallowed for pronouns aswell to avoid confusion

        //ret.put("rainbow", ChatColors.RAINBOW); // Bugged in ReIndev 2.8.1_04, 2.8.1_05

        return ret;
    }

    public LinkedHashMap<String, String> getFlagColorChoiceNames(){
        LinkedHashMap<String, String> ret = getNameAndPronounColorChoiceNames();

        // I chose to use "lightred"=RED instead of making "red"=RED, "darkred"=DARK_RED
        // So that it would be consistent with the /namecolor, /pronounscolor commands
        ret.put("lightred", ChatColors.RED);
        return ret;
    }
}
