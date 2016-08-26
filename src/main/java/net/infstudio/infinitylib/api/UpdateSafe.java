package net.infstudio.infinitylib.api;

import net.minecraft.util.ITickable;

/**
 * @author ci010
 */
public interface UpdateSafe extends ITickable
{
	boolean shouldUpdate();
}
