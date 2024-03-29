package com.kiva.kivaserverutils.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.level.Explosion;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.chunk.ChunkPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(Explosion.class)
public abstract class MixinExplosion {
    @Shadow private World worldObj;

    // TODO: Send block updates to client to quickly synchronize
    @Redirect(method = "doExplosion", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", ordinal = 0))
    public boolean excludeBlocks$doExplosion(Set<ChunkPosition> instance, Object e){
        ChunkPosition pos = (ChunkPosition)e;

        if (KivaServerUtils.inProtectedRegion(pos.x, pos.y, pos.z, worldObj.dimension).second)
            return true;

        if (KivaServerUtils.getConfigValue("explosionsbreakchests"))
            return instance.add(pos);

        final int blockID = worldObj.getBlockId(pos.x, pos.y, pos.z);
        if (blockID != 54 && blockID != 55 && blockID != 240 && blockID != 249)
            return instance.add(pos);
        return true;
    }
}
