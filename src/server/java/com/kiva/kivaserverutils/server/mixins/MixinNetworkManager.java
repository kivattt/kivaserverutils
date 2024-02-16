package com.kiva.kivaserverutils.server.mixins;

import net.minecraft.src.server.packets.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @ModifyConstant(method = "processReadPackets", constant = @Constant(intValue = 1048576))
    private int increaseSendSizeOverflowThreshold(int value){
        return Integer.MAX_VALUE; // Arbitrary and might cause problems in the future
    }
}
