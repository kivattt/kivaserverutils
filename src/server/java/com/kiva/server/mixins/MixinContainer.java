package com.kiva.server.mixins;

import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.recipe.ICrafting;
import net.minecraft.src.server.playergui.Container;
import net.minecraft.src.server.playergui.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

@Mixin(Container.class)
public abstract class MixinContainer{
    @Shadow
    public List<Slot> inventorySlots;

    @Shadow
    public List<Object> inventoryItemStacks;

    @Shadow
    protected List<ICrafting> crafters;

    @Shadow public int windowId;

    /**
     * @author Kiva
     * @reason More detailed logging for chest/crate interaction
     */
    @Overwrite
    public void updateInventory(){
        String containerItemsChangedLog = "";

        for(int var1 = 0; var1 < this.inventorySlots.size(); ++var1) {
            ItemStack itemStack = this.inventorySlots.get(var1).getStack();
            ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);
            if (!ItemStack.areItemStacksEqual(var3, itemStack)) {
                String containerTypeName = "Unknown";
                if (this.inventoryItemStacks.size() == 45) {
                    containerTypeName = "Crate";
                }
                else if (this.inventoryItemStacks.size() == 63) {
                    containerTypeName = "Chest";
                }
                else if (this.inventoryItemStacks.size() == 90) {
                    containerTypeName = "Double chest";
                }

                final boolean fromHandleWindowClick = Thread.currentThread().getStackTrace()[2].getMethodName().equals("handleWindowClick"); // Least hacky foxloader mod
                final boolean itemChangedWasPlayersInventory = var1 < this.inventoryItemStacks.size() && var1 >= this.inventoryItemStacks.size() - 36;
                final boolean eitherIsNull = var3 == null || itemStack == null;
                boolean disqualifyForEqual = false;
                if (!eitherIsNull) {
                    disqualifyForEqual = (var3.itemID == itemStack.itemID && var3.stackSize == itemStack.stackSize);
                }
                if (fromHandleWindowClick && !itemChangedWasPlayersInventory && containerTypeName != "Unknown" && !disqualifyForEqual /*&& username != ""*/ && this.windowId != 0) {
                    containerItemsChangedLog = containerItemsChangedLog + "\nPlayer" + /*username +*/ " in " + containerTypeName + ", item " + ((var3 == null) ? "null" : var3) + " replaced with " + ((itemStack == null) ? "null" : itemStack);
                }

                var3 = itemStack == null ? null : itemStack.copy();
                this.inventoryItemStacks.set(var1, var3);

                for(int var4 = 0; var4 < this.crafters.size(); ++var4) {
                    this.crafters.get(var4).updateCraftingInventorySlot((Container)(Object)this, var1, var3);
                }
            }
        }

        if (containerItemsChangedLog != "") {
            final Logger logger = Logger.getLogger("Minecraft");
            logger.warning("Inventory update:" + containerItemsChangedLog);
        }
    }
}

