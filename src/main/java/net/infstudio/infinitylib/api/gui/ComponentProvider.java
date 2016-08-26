package net.infstudio.infinitylib.api.gui;

import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;

import java.util.List;

/**
 * @author ci010
 */
public interface ComponentProvider
{
	void provideComponents(List<GuiComponent> components);
}
