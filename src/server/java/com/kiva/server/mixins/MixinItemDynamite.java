package com.kiva.server.mixins;

import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemDynamite;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.level.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.logging.Level;
import java.util.logging.Logger;

@Mixin(ItemDynamite.class)
public class MixinItemDynamite {
    @Inject(method = "onItemRightClick", at = @At("RETURN"))
    public void dynamiteLogging(ItemStack itemstack, World world, EntityPlayer entityplayer, CallbackInfoReturnable<ItemStack> cir){
        Logger.getLogger("Minecraft").log(Level.WARNING, entityplayer.username + " used DYNAMITE @ x:" + entityplayer.posX + ", y:" + entityplayer.posY + ", z:" + entityplayer.posZ);
    }
}
