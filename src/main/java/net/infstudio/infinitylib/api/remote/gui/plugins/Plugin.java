package net.infstudio.infinitylib.api.remote.gui.plugins;

import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;

/**
 * @author ci010
 */
public interface Plugin
{
	void plugin(GuiComponent component);

	void dispose();
}
