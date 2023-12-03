package com.kiva.server.mixins;

import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.level.WorldServer;
import net.minecraft.src.game.level.chunk.ChunkCoordinates;
import net.minecraft.src.server.ServerConfigurationManager;
import net.minecraft.src.server.packets.NetLoginHandler;
import net.minecraft.src.server.player.PlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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


    /*@Shadow private MinecraftServer mcServer;

    @Shadow public int worldType;*/

    /*@Inject(method = "recreatePlayerEntity", at = @At("HEAD"))
    public void onRecreatePlayerEntity(EntityPlayerMP entityPlayerMP, int arg2, CallbackInfoReturnable<EntityPlayerMP> cir){
        // On respawn when the player has never slept or *after* theyve lost spawn point due to a broken bed
        if (entityPlayerMP.getSpawnChunk() == null)
            System.out.println("SPAWN CHUNK WAS NULL!!!!!");
        else
            System.out.println("SPAWN CHUNK WASNT NULL... here it is: " + entityPlayerMP.getSpawnChunk());
    }*/

    // On respawn when the player has lost their spawn point (bed broken)
    /*@Inject(method = "recreatePlayerEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/server/packets/NetServerHandler;sendPacket(Lnet/minecraft/src/server/packets/Packet;)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void h(EntityPlayerMP entityPlayerMP, int arg2, CallbackInfoReturnable<EntityPlayerMP> cir, ChunkCoordinates chunkCoordinates, EntityPlayerMP var4, WorldServer worldServer, ChunkCoordinates var6){
        //worldServer = mcServer.getWorldManager(KivaServerUtils.spawnCommandLocation.dimension);

        var4.setSpawnChunk(new ChunkCoordinates((int) KivaServerUtils.spawnCommandLocation.x, (int) KivaServerUtils.spawnCommandLocation.y, (int) KivaServerUtils.spawnCommandLocation.z));
        //var4.teleportRegistered(KivaServerUtils.spawnCommandLocation.x, KivaServerUtils.spawnCommandLocation.y, KivaServerUtils.spawnCommandLocation.z);
        if (var4.dimension != KivaServerUtils.spawnCommandLocation.dimension)
            var4.sendPlayerThroughPortalRegistered();

        var4.posX = KivaServerUtils.spawnCommandLocation.x;
        var4.posY = KivaServerUtils.spawnCommandLocation.y;
        var4.posZ = KivaServerUtils.spawnCommandLocation.z;

        //cir.getReturnValue().dimension = KivaServerUtils.spawnCommandLocation.dimension;

        //cir.cancel();
    }*/

    /*
    @Inject(method = "recreatePlayerEntity", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void onEnd(EntityPlayerMP entityPlayerMP, int arg2, CallbackInfoReturnable<EntityPlayerMP> cir, ChunkCoordinates chunkCoordinates, EntityPlayerMP var4, WorldServer worldServer){
        System.out.println("worldServer dimension: " + worldServer.dimension);
    }*/
}
