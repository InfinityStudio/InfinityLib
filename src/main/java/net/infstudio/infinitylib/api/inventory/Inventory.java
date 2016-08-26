package net.infstudio.infinitylib.api.inventory;

import net.infstudio.infinitylib.api.Callback;
import net.minecraft.inventory.ISidedInventory;
import net.minecraftforge.items.IItemHandler;

/**
 * @author ci010
 */
public interface Inventory extends IItemHandler, Iterable<InventoryElement>, ISidedInventory
{
	Callback.Container getCallback();

	Layout getLayout();
}
