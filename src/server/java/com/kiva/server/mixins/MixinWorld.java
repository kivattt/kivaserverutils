package com.kiva.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.chunk.ChunkCoordinates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class MixinWorld {
    @Inject(method = "getSpawnPoint", at = @At("HEAD"), cancellable = true)
    public void overRideTheSpawnPoint(CallbackInfoReturnable<ChunkCoordinates> cir){
        if (KivaServerUtils.spawnCommandLocation == null)
            return;

        // Don't override the spawn location if spawn was set in the nether
        if (KivaServerUtils.spawnCommandLocation.dimension != 0)
            return;

        cir.setReturnValue(new ChunkCoordinates((int) KivaServerUtils.spawnCommandLocation.x, (int) KivaServerUtils.spawnCommandLocation.y, (int) KivaServerUtils.spawnCommandLocation.z));
        cir.cancel();
    }
}
