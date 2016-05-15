package test.api.component.item.module.drop;

import net.minecraft.entity.item.EntityItem;
import test.api.component.item.StateItem;
import test.api.world.World;

/**
 * @author ci010
 */
public interface ILifespan
{
	/**
	 * Retrieves the normal 'lifespan' of this item when it is dropped on the ground as a EntityItem.
	 * This is in ticks, standard result is 6000, or 5 mins.
	 *
	 * @param itemStack The current ItemStack
	 * @param world     The world the entity is in
	 * @return The normal lifespan in ticks.
	 */
	int getEntityLifespan(StateItem itemStack, World world);

	/**
	 * Called by the default implemetation of EntityItem's onUpdate method, allowing for cleaner
	 * control over the update of the item without having to write a subclass.
	 *
	 * @param entityItem The entity Item
	 * @return Return true to skip any further update code.
	 */
	boolean onEntityItemUpdate(EntityItem entityItem);
}