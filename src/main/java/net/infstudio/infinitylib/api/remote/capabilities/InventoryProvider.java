package net.infstudio.infinitylib.api.remote.capabilities;

import net.infstudio.infinitylib.api.inventory.Inventory;
import net.infstudio.infinitylib.api.inventory.InventoryBuilder;

/**
 * @author ci010
 */
@CapabilityInjectInterface(Inventory.class)
public interface InventoryProvider
{
	void buildInventory(InventoryBuilder builder);
}
