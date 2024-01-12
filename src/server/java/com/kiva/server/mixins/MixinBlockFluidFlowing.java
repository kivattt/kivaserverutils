package com.kiva.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.block.BlockFluidFlowing;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFluidFlowing.class)
public class MixinBlockFluidFlowing {
    // Prevent water flowing into a protected region from triggering the item drop for the block e.g. flowers
    @Inject(method = "flowIntoBlock", at = @At("HEAD"), cancellable = true)
    public void preventWaterItemDupe(World world, int x, int y, int z, int metad, CallbackInfo ci){
        if (KivaServerUtils.inProtectedRegion(x, y, z, world.dimension).second)
            ci.cancel();
    }
}
