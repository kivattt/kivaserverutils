package com.kiva.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.server.ServerConfigurationManager;
import net.minecraft.src.server.packets.NetLoginHandler;
import net.minecraft.src.server.player.PlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerConfigurationManager.class)
public class MixinServerConfigurationManager {
    // Scrapped spawn location dimension code
    /*@Shadow private MinecraftServer mcServer;

    // If this is the player's first time joining, spawn in the dimension specified with /spawnset
    @Inject(method = "login", at = @At("TAIL"), cancellable = true)
    public void handlePlayerFirstJoined(NetLoginHandler netLoginHandler, String arg2, CallbackInfoReturnable<EntityPlayerMP> cir){
        if (KivaServerUtils.spawnCommandLocation == null)
            return;

        cir.setReturnValue(new EntityPlayerMP(
                this.mcServer,
                this.mcServer.getWorldManager(KivaServerUtils.spawnCommandLocation.dimension),
                arg2,
                new PlayerController(this.mcServer.getWorldManager(KivaServerUtils.spawnCommandLocation.dimension))
        ));
        cir.cancel();
    }*/
}
