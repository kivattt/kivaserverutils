package com.kiva.server.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.ThreadHeavy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThreadHeavy.class)
public class MixinThreadHeavy {
    @Shadow public boolean isAlive;

    @Inject(method = "run", at = @At(value = "FIELD", target = "Lnet/minecraft/server/ThreadHeavy;isAlive:Z", ordinal = 0))
    public void sleepInThreadHeavy(CallbackInfo ci){
        if (isAlive) {
            try {
                Thread.sleep(1L); // Pretty arbitrary, but has to be pretty low so closing the server doesnt take forever to save chunks
            } catch (final InterruptedException ex) {
            }
        }
    }
}
