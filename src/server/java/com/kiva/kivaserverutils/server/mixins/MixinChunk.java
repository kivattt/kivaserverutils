package com.kiva.kivaserverutils.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chunk.class)
public class MixinChunk {
    @Shadow @Final public int xPosition;

    @Shadow @Final public int yPosition;

    @Shadow @Final public int zPosition;

    @Shadow public World worldObj;

    // TODO: Make DRY
    @Inject(method = "setBlockID", at = @At("HEAD"), cancellable = true)
    public void onSetBlockID(int x, int y, int z, int id, CallbackInfoReturnable<Boolean> cir) {
        // Let's check this manually first to avoid calculating the block coordinates
        if (KivaServerUtils.protectedRegions.isEmpty())
            return;

        int blockX = xPosition << 4 | x;
        int blockY = yPosition << 4 | y;
        int blockZ = zPosition << 4 | z;

        if (KivaServerUtils.inProtectedRegion(blockX, blockY, blockZ, worldObj.dimension).second){
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    // TODO: Make DRY
    @Inject(method = "setBlockIDWithMetadata", at = @At("HEAD"), cancellable = true)
    public void onSetBlockIDWithMetadata(int x, int y, int z, int blockid, int metadata, CallbackInfoReturnable<Boolean> cir){
        // Let's check this manually first to avoid creating & calculating the block coordinates
        if (KivaServerUtils.protectedRegions.isEmpty())
            return;

        int blockX = xPosition << 4 | x;
        int blockY = yPosition << 4 | y;
        int blockZ = zPosition << 4 | z;

        if (KivaServerUtils.inProtectedRegion(blockX, blockY, blockZ, worldObj.dimension).second) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
