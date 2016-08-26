package net.infstudio.infinitylib.api.remote.gui.node;

import net.infstudio.infinitylib.api.Pipeline;
import net.infstudio.infinitylib.api.remote.gui.Properties;
import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;
import net.minecraft.client.gui.Gui;

/**
 * @author ci010
 */
public class DrawTexture extends Gui implements DrawNode
{
	public static final DrawTexture INSTANCE = new DrawTexture();

	private DrawTexture() {}

	@Override
	public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties)
	{
//		VarForward<TextureInfo> property = properties.property(ComponentAPI.PROP_TEXTURE);
//		if (property.isPresent())
//		{
//			TextureInfo texture = property.get();
//			GuiUtil.bindTexture(texture);
//			this.drawTexturedModalRect(x, y,
//					texture.getU(), texture.getV(), texture.getHeight(), texture.getHeight());
//		}
	}
}
