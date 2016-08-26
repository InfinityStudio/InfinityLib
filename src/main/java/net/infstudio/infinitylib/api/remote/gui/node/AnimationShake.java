package net.infstudio.infinitylib.api.remote.gui.node;

import net.infstudio.infinitylib.api.Pipeline;
import net.infstudio.infinitylib.api.remote.gui.Properties;
import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author ci010
 */
public class AnimationShake implements DrawNode
{
	@Override
	public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties)
	{
		Boolean last = properties.getCache("last-shake");
		if (last == null)
		{
			properties.putCache("last-shake", Boolean.FALSE);
			last = Boolean.FALSE;
		}
		if (last)
			GlStateManager.translate(1, 0, 0);
		else
			GlStateManager.translate(-1, 0, 0);
	}
}
