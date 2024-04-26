package com.kiva.kivaserverutils.server.mixins;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.Pair;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.ChatAllowedCharacters;
import net.minecraft.src.server.packets.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(NetServerHandler.class)
public abstract class MixinNetServerHandler {
    @Shadow
    private EntityPlayerMP playerEntity;

    @Shadow
    public MinecraftServer mcServer;

    @Shadow private boolean hasMoved;

    @Inject(method = "handleChat", at = @At("HEAD"), cancellable = true)
    public void handleMutedPlayers(Packet3Chat packet3Chat, CallbackInfo ci){
        if (!KivaServerUtils.isPlayerMuted(playerEntity.username))
            return;

        String msgTrim = packet3Chat.message.trim();

        // Prevent /me or /tell commands
        // TODO: Clean this up to use some proper command system?
        List<String> commandsDisallowedWhileMuted = Arrays.asList("me", "tell", "w", "msg");

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
        String flag = KivaServerUtils.getPlayerFlag(this.playerEntity.username);
        String pronounColor = KivaServerUtils.getPlayerPronounColor(this.playerEntity.username);

        if (this.mcServer.configManager.isOp(this.playerEntity.username) && KivaServerUtils.playerNameColors.get(this.playerEntity.username) == null)
            nameColor = ChatColors.RED;

        String playerNameUnlessNickname = KivaServerUtils.playerNicknames.get(this.playerEntity.username) == null ? this.playerEntity.username : KivaServerUtils.playerNicknames.get(this.playerEntity.username);

        if (pronouns != null) {
            return ChatColors.RESET + "[" + pronounColor + pronouns + ChatColors.RESET + "] " + flag + ChatColors.RESET + "<" + nameColor + playerNameUnlessNickname + ChatColors.RESET + "> " + packet3Chat.message.trim();
        } else {
            return ChatColors.RESET + flag + ChatColors.RESET + "<" + nameColor + playerNameUnlessNickname + ChatColors.RESET + "> " + packet3Chat.message.trim();
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

        StringBuilder signText = new StringBuilder();

        for (int i = 0; i < Math.min(4, packet130UpdateSign.signLines.length); i++){
            String line = packet130UpdateSign.signLines[i];
            if (line.length() > 15)
                continue;

            for (int j = 0; j < line.length(); j++){
                if (ChatAllowedCharacters.allowedCharacters.indexOf(line.charAt(j)) < 0)
                    continue;

                signText.append(line.charAt(j));
            }

            signText.append("\n");
        }

        ServerMod.getGameInstance().logWarning(playerEntity.username + " placed/edited sign @ x:" + packet130UpdateSign.xPosition + ", y:" + packet130UpdateSign.yPosition + ", z:" + packet130UpdateSign.zPosition + " with text:\n" + signText);

        Pair<String, Boolean> protectedRegion = KivaServerUtils.inProtectedRegion(packet130UpdateSign.xPosition, packet130UpdateSign.yPosition, packet130UpdateSign.zPosition, playerEntity.dimension);

        if (protectedRegion.second){
            playerEntity.displayChatMessage(KivaServerUtils.notifyProtectedRegion + protectedRegion.first);
            ci.cancel();
        }
    }

    @Inject(method = "handleCauldron", at = @At("HEAD"), cancellable = true)
    public void onHandleCauldron(Packet66Cauldron packet66Cauldron, CallbackInfo ci){
        if (KivaServerUtils.isPlayerInRestrictiveMode(playerEntity.username)){
            playerEntity.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);
            ci.cancel();
        }

        Pair<String, Boolean> protectedRegion = KivaServerUtils.inProtectedRegion(packet66Cauldron.xPosition, packet66Cauldron.yPosition, packet66Cauldron.zPosition, playerEntity.dimension);

        if (protectedRegion.second){
            playerEntity.displayChatMessage(KivaServerUtils.notifyProtectedRegion + protectedRegion.first);
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

    @Inject(method = "handleFlying", at = @At("HEAD"))
    public void onPlayerMoved(Packet10Flying packet10Flying, CallbackInfo ci) {
        // This is just a pitch & yaw packet, ignore it
        if (packet10Flying instanceof Packet12PlayerLook)
            return;

        // Seems like this is controlled serverside, even though the client can send it
        if (!packet10Flying.moving)
            return;

        // Seems to be for the initial join stage where the client sends position packets like x 0 z 0
        if (!this.hasMoved)
            return;

        if (!KivaServerUtils.afkPlayers.containsKey(playerEntity.username))
            return;

        // Atleast 1 second has to pass to un-AFK
        if (System.currentTimeMillis() - KivaServerUtils.afkPlayers.get(playerEntity.username) < 1000)
            return;

        double dX = Math.abs(packet10Flying.xPosition - playerEntity.posX);
        double dY = Math.abs(packet10Flying.yPosition - playerEntity.posY);
        double dZ = Math.abs(packet10Flying.zPosition - playerEntity.posZ);

        final double threshold = 0.01;
        if (dX > threshold || dY > threshold || dZ > threshold) {
            KivaServerUtils.afkPlayers.remove(playerEntity.username);
            ServerMod.getGameInstance().configManager.sendPacketToAllPlayers(new Packet3Chat(ChatColors.GRAY + playerEntity.username + " is no longer AFK"));
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