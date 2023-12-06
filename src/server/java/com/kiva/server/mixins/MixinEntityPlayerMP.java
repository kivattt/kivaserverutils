package com.kiva.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP {
    @Inject(method = "handleFalling", at = @At("HEAD"), cancellable = true)
    public void onHandleFalling(double arg1, boolean arg3, CallbackInfo ci){
        if (KivaServerUtils.getConfigValue("falldamagedisabled"))
            ci.cancel();
    }
}
