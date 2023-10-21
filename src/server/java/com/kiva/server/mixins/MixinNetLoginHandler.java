package com.kiva.server.mixins;

import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.packets.NetLoginHandler;
import net.minecraft.src.server.packets.Packet1Login;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NetLoginHandler.class)
public class MixinNetLoginHandler {
    // Scrapped spawn location dimension code
    /*@Inject(method = "doLogin", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void debugMoment(Packet1Login packet1Login, CallbackInfo ci, EntityPlayerMP entityPlayerMP){
        System.out.println("Dimension in doLogin in the end: " + entityPlayerMP.dimension);
    }*/
}
