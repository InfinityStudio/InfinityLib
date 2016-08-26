package net.infstudio.infinitylib.api.gui.node;

import net.infstudio.infinitylib.api.Pipeline;
import net.infstudio.infinitylib.api.remote.gui.Properties;
import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;
import net.infstudio.infinitylib.api.remote.gui.node.DrawNode;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawString extends Gui implements DrawNode
{
	public static final DrawString INSTANCE = new DrawString();

	private DrawString() {}

	@Override
	public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties) {
//		TODO Error
//		VarForward<CharSequence> property = properties.property(ComponentAPI.PROP_STRING);
//		if (property.isPresent())
//			this.drawString(GuiUtil.font(), property.get().toString(), x, y, 0);
	}
}
