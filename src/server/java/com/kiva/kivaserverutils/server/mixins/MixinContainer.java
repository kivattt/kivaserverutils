package com.kiva.kivaserverutils.server.mixins;

import com.kiva.kivaserverutils.ItemStackWithoutStackSize;
import com.kiva.kivaserverutils.KivaServerUtils;
import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.recipe.ICrafting;
import net.minecraft.src.server.playergui.Container;
import net.minecraft.src.server.playergui.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.List;
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
        HashMap<ItemStackWithoutStackSize, Integer> diff = new HashMap<>();

        String containerTypeName = "Unknown";
        if (inventoryItemStacks.size() == 45)
            containerTypeName = "Crate";
        else if (inventoryItemStacks.size() == 63)
            containerTypeName = "Chest";
        else if (inventoryItemStacks.size() == 90)
            containerTypeName = "Double chest";

        for(int var1 = 0; var1 < this.inventorySlots.size(); ++var1) {
            ItemStack itemTo = this.inventorySlots.get(var1).getStack();
            ItemStack itemFrom = (ItemStack)this.inventoryItemStacks.get(var1);
            if (!ItemStack.areItemStacksEqual(itemFrom, itemTo)) {
                final boolean fromHandleWindowClick = Thread.currentThread().getStackTrace()[2].getMethodName().equals("handleWindowClick"); // Least hacky foxloader mod
                final boolean itemChangedWasPlayersInventory = var1 < this.inventoryItemStacks.size() && var1 >= this.inventoryItemStacks.size() - 36;
                final boolean eitherIsNull = itemFrom == null || itemTo == null;
                boolean disqualifyForEqual = false;
                if (!eitherIsNull)
                    disqualifyForEqual = itemFrom.isStackEqual(itemTo);

                if (!disqualifyForEqual && fromHandleWindowClick && !itemChangedWasPlayersInventory && !containerTypeName.equals("Unknown") && KivaServerUtils.handleWindowClickLatestPlayerUsername != null && this.windowId != 0) {
                    if (itemFrom != null) {
                        int newStackSize;
                        if (!diff.containsKey(new ItemStackWithoutStackSize(itemFrom)))
                            newStackSize = -itemFrom.stackSize;
                        else
                            newStackSize = diff.get(new ItemStackWithoutStackSize(itemFrom)) - itemFrom.stackSize;

                        diff.put(new ItemStackWithoutStackSize(itemFrom), newStackSize);
                    }

                    if (itemTo != null) {
                        int newStackSize;
                        if (!diff.containsKey(new ItemStackWithoutStackSize(itemTo)))
                            newStackSize = itemTo.stackSize;
                        else
                            newStackSize = diff.get(new ItemStackWithoutStackSize(itemTo)) + itemTo.stackSize;

                        diff.put(new ItemStackWithoutStackSize(itemTo), newStackSize);
                    }
                }

                itemFrom = itemTo == null ? null : itemTo.copy();
                this.inventoryItemStacks.set(var1, itemFrom);

                for (ICrafting crafter : this.crafters)
                    crafter.updateCraftingInventorySlot((Container) (Object) this, var1, itemFrom);
            }
        }

        diff.values().remove(0);
        if (diff.isEmpty())
            return;

        StringBuilder containerItemsChangedLog = new StringBuilder();
        diff.forEach((key, value) -> {
            String putInOrTookOut = value < 0 ? "Took out " : "Put in ";
            containerItemsChangedLog.append("\n").append(putInOrTookOut).append(itemStackToString(key.asItemStack(Math.abs(value))));
        });

        if (containerItemsChangedLog.length() > 0) {
            final Logger logger = Logger.getLogger("Minecraft");
            logger.warning(KivaServerUtils.handleWindowClickLatestPlayerUsername + " in " + containerTypeName + ":" + containerItemsChangedLog);
        }
    }

    @Unique
    public String itemStackToString(ItemStack itemStack) {
        return itemStack.stackSize + " " + itemNameWithoutTileOrItemPrefix(itemStack.getDisplayName()) + (itemStack.itemDamage == 0 ? "" : " @ " + itemStack.itemDamage);
    }

    @Unique
    public String itemNameWithoutTileOrItemPrefix(String itemName) {
        if (itemName.startsWith("tile."))
            return itemName.substring("tile.".length());

        if (itemName.startsWith("item."))
            return itemName.substring("item.".length());

        return itemName;
    }
}