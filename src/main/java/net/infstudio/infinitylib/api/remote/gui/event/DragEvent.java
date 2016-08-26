package net.infstudio.infinitylib.api.remote.gui.event;


import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.MouseEvent;

/**
 * @author ci010
 * @see net.minecraftforge.client.event.GuiScreenEvent
 * @see net.minecraftforge.client.event.MouseEvent
 */
public class DragEvent extends MouseEvt
{
	public DragEvent(MouseEvent event, GuiComponent component, GuiScreen screen)
	{
		super(event, component, screen);
	}

	public long getDraggingTime()
	{
		return this.getFullState().nanoseconds;
	}
}
