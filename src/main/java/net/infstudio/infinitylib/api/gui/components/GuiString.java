package net.infstudio.infinitylib.api.gui.components;

import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;
import net.infstudio.infinitylib.api.utils.GuiUtil;
import net.minecraft.inventory.Container;

/**
 * One of the most basic components in gui.
 * This just simply draw a string.
 *
 * @author ci010
 */
public class GuiString extends GuiComponent
{
	protected CharSequence key;

	public GuiString(CharSequence key, int x, int y)
	{
		this.key = key;
        //TODO Fix this error
//		if (key instanceof StringSource)
//			this.getProperties().property(ComponentAPI.PROP_STRING_SRC).set(((StringSource) key).source().getSource());
//		this.getProperties().property(ComponentAPI.PROP_STRING).set(key);
		this.setPosRelative(x, y);
		this.transform.height = 8;
	}

	public void onLoad(Container container)
	{
		String content = key.toString();
		int width;
		if (this.transform.width != (width = GuiUtil.font().getStringWidth(content)))
			this.transform.width = width;
		this.transform.width = width;
	}
}
