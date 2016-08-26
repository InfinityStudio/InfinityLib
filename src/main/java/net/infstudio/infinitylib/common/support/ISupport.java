package net.infstudio.infinitylib.common.support;

import net.minecraftforge.fml.common.LoaderState;

/**
 * @author ci010
 */
public interface ISupport
{
	String getSupportModId();

	void onModLoad(LoaderState state);
}
