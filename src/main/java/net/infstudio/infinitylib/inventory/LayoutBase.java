package net.infstudio.infinitylib.inventory;

import net.infstudio.infinitylib.api.inventory.Layout;
import net.infstudio.infinitylib.common.Vector2i;

import java.util.ArrayList;

/**
 * @author ci010
 */
public class LayoutBase implements Layout
{
	public ArrayList<Vector2i> list = new ArrayList<Vector2i>();

	@Override
	public Vector2i getPos(int id)
	{
		return list.get(id);
	}
}
