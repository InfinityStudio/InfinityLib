package net.infstudio.infinitylib.api.remote.gui.node;

import net.infstudio.infinitylib.api.Pipeline;
import net.infstudio.infinitylib.api.remote.gui.Properties;
import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;

/**
 * @author ci010
 */
public interface DrawNode
{
	void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties);
}
