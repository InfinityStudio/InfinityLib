package net.infstudio.infinitylib.api.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

/**
 * @author ci010
 */
public interface InventorySpace extends IItemHandler, Iterable<ItemStack>, InventoryElement
{
	int xSize();

	int ySize();
}
