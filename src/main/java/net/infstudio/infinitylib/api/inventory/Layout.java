package net.infstudio.infinitylib.api.inventory;

import net.infstudio.infinitylib.api.utils.NotNull;
import net.infstudio.infinitylib.common.Vector2i;

/**
 * @author ci010
 */
public interface Layout
{
	Vector2i NULL = new Vector2i(-1, -1);

	@NotNull
	Vector2i getPos(int id);
}
