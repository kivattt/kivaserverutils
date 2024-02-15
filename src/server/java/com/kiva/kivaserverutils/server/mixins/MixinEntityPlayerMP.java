package com.kiva.kivaserverutils.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.packets.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP {
    @Shadow public NetServerHandler playerNetServerHandler;

    @Inject(method = "handleFalling", at = @At("HEAD"), cancellable = true)
    public void onHandleFalling(double arg1, boolean arg3, CallbackInfo ci){
        if (KivaServerUtils.getConfigValue("falldamagedisabled"))
            ci.cancel();
    }

    @ModifyConstant(method = "onUpdateEntity", constant = @Constant(intValue = 24))
    private int removeChunkSendingLimits1(int value){
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "onUpdateEntity", constant = @Constant(intValue = 40))
    private int removeChunkSendingLimits2(int value){
        return Integer.MAX_VALUE;
    }

    // Scrapped code to add nicknames to death msgs, this might come in a later update to KivaServerUtils
    /*
    @ModifyArg(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/server/ServerConfigurationManager;sendPacketToAllPlayers(Lnet/minecraft/src/server/packets/Packet;)V"))
    public Packet addNicknameToDeathMsgs(Packet in){
        return new Packet3Chat(this.playerNetServerHandler.getUsername() + ((Packet3Chat)in).message);
    }*/
}
