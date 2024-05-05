package com.kiva.kivaserverutils;

import net.minecraft.src.game.item.ItemStack;
import net.minecraft.src.game.nbt.NBTTagCompound;

import java.util.Objects;

public class ItemStackWithoutStackSize {
    public int itemID;
    public int itemDamage;
    public NBTTagCompound nbtTagCompound;
    private final int hashCode;

    public ItemStackWithoutStackSize(ItemStack itemStack) {
        this.itemID = itemStack.itemID;
        this.itemDamage = itemStack.itemDamage;
        this.nbtTagCompound = itemStack.nbtTagCompound;
        this.hashCode = Objects.hash(this.itemID, this.itemDamage, this.nbtTagCompound);
    }

    public ItemStack asItemStack(int withStackSize) {
        return new ItemStack(this.itemID, withStackSize, this.itemDamage, this.nbtTagCompound);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        ItemStackWithoutStackSize that = (ItemStackWithoutStackSize) obj;
        return itemID == that.itemID && itemDamage == that.itemDamage && nbtTagCompound == that.nbtTagCompound;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
