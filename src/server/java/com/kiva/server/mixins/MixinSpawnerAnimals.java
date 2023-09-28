package com.kiva.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.block.tileentity.TileEntity;
import net.minecraft.src.game.block.tileentity.TileEntityChest;
import net.minecraft.src.game.entity.EnumCreatureType;
import net.minecraft.src.game.level.SpawnerAnimals;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.level.features.WorldGenDungeons;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(SpawnerAnimals.class)
public abstract class MixinSpawnerAnimals {
    @Redirect(method = "performSpawning", at = @At(value = "INVOKE", target = "Ljava/util/Set;size()I"))
    private static int mobcap(Set instance, World world){
        if (KivaServerUtils.getConfigValue("mobcapdisabled"))
            return instance.size();

        // Unfortunately a little stepped since this has to return int, but should not be an issue
        return (int)((Math.pow(world.playerEntities.size(), 0.7) * 1.5) * 256);
    }

    @Redirect(method = "performSpawning", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/game/entity/EnumCreatureType;getMaxNumberOfCreature()I"))
    private static int modifyMaxNumberOfCreatures(EnumCreatureType instance){
        int maxNumberOfCreatures = instance.getMaxNumberOfCreature();
        if (KivaServerUtils.getConfigValue("mobcapdisabled"))
            return maxNumberOfCreatures;

        if (maxNumberOfCreatures == 100) // monster
            return 23; // Somewhat arbitrary
        if (maxNumberOfCreatures == 20) // creature
            return 14; // Somewhat arbitrary
        return maxNumberOfCreatures; // waterCreature
    }
}
