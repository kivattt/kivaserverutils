package com.kiva.server.mixins;

import com.fox2code.foxloader.network.ChatColors;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.KivaServerUtilsServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.packets.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(NetServerHandler.class)
public abstract class MixinNetServerHandler {
    @Shadow
    private EntityPlayerMP playerEntity;

    @Shadow
    public MinecraftServer mcServer;

    @Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
    public void handleMutedPlayers(Packet3Chat packet3Chat, CallbackInfo ci){
        if (!KivaServerUtils.isPlayerMuted(playerEntity.username))
            return;

        String msgTrim = packet3Chat.message.trim();

        // Prevent /me or /tell commands
        // TODO: Clean this up to use some proper command system?
        List<String> commandsDisallowedWhileMuted = new ArrayList<>();
        commandsDisallowedWhileMuted.add("me");
        commandsDisallowedWhileMuted.add("tell");
        commandsDisallowedWhileMuted.add("w");   // Alias for /tell
        commandsDisallowedWhileMuted.add("msg"); // Alias for /tell

        for (String command : commandsDisallowedWhileMuted){
            if (msgTrim.toLowerCase().startsWith("/" + command + " ")){
                playerEntity.displayChatMessage(ChatColors.RED + "You are currently muted");
                ci.cancel();
                return;
            }
        }

        // Don't prevent other chat commands
        if (msgTrim.startsWith("/"))
            return;

        playerEntity.displayChatMessage(ChatColors.RED + "You are currently muted");
        ci.cancel();
    }

    @ModifyVariable(method = "handleChat", at = @At(value = "STORE", ordinal = 2))
    private String customChat$handleChat(String value, Packet3Chat packet3Chat){
        String pronouns = KivaServerUtils.playerPronouns.get(this.playerEntity.username);
        String nameColor = KivaServerUtils.getPlayerNameColor(this.playerEntity.username);
        if (this.mcServer.configManager.isOp(this.playerEntity.username) && KivaServerUtils.playerNameColors.get(this.playerEntity.username) == null)
            nameColor = ChatColors.RED;

        String playerNameUnlessNickname = KivaServerUtils.playerNicknames.get(this.playerEntity.username) == null ? this.playerEntity.username : KivaServerUtils.playerNicknames.get(this.playerEntity.username);

        if (pronouns != null) {
            return "[" + ChatColors.GREEN + pronouns + ChatColors.RESET + "] <" + nameColor + playerNameUnlessNickname + ChatColors.RESET + "> " + packet3Chat.message.trim();
        }else {
            return "<" + nameColor + playerNameUnlessNickname + ChatColors.RESET + "> " + packet3Chat.message.trim();
        }
    }

    @ModifyArg(method = "handleChat", at = @At(value = "INVOKE", target = "Ljava/util/logging/Logger;info(Ljava/lang/String;)V"))
    private String logOriginalUsername$handleChat(String in){
        return this.playerEntity.username + " > " + in;
    }

    // Incredibly hacky
    @Inject(method = "handleWindowClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/server/playergui/Container;updateInventory()V"))
    public void storeLatestPlayerUsername(Packet102WindowClick packet102WindowClick, CallbackInfo ci){
        KivaServerUtils.handleWindowClickLatestPlayerUsername = this.playerEntity.username;
    }

    @Inject(method = "handleUpdateSign", at = @At("HEAD"), cancellable = true)
    public void onEditSign(Packet130UpdateSign packet130UpdateSign, CallbackInfo ci){
        if (KivaServerUtils.isPlayerInRestrictiveMode(playerEntity.username)) {
            playerEntity.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);
            ci.cancel();
        }
    }

    @Inject(method = "handleUseEntity", at = @At("HEAD"), cancellable = true)
    public void onUseEntity(Packet7UseEntity packet7, CallbackInfo ci){
        if (KivaServerUtils.isPlayerInRestrictiveMode(playerEntity.username)) {
            playerEntity.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);
            ci.cancel();
        }
    }

    // Scrapped spawn location dimension code
    /*@ModifyArg(method = "handleRespawnPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/server/ServerConfigurationManager;recreatePlayerEntity(Lnet/minecraft/src/game/entity/player/EntityPlayerMP;I)Lnet/minecraft/src/game/entity/player/EntityPlayerMP;"), index = 1)
    public int setDimensionOnRespawn(int arg2){
        if (KivaServerUtils.spawnCommandLocation == null)
            return 0; // Vanilla behaviour

        return KivaServerUtils.spawnCommandLocation.dimension;
    }*/
}