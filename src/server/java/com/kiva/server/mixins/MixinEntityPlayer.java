package com.kiva.server.mixins;

import com.fox2code.foxloader.loader.ServerMod;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.KivaServerUtilsServer;
import net.minecraft.src.game.entity.Entity;
import net.minecraft.src.game.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {
    @Shadow public String username;

    // Prevent players in restrictive mode from attacking entities
    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"), cancellable = true)
    public void onAttackTargetEntityWithCurrentItem(Entity entity, CallbackInfo ci){
        if (KivaServerUtils.isPlayerInRestrictiveMode(username)) {
            ServerMod.getGameInstance().configManager.sendChatMessageToPlayer(username, KivaServerUtils.notifyPlayerIsInRestrictiveMode);
            ci.cancel();
        }
    }
}
