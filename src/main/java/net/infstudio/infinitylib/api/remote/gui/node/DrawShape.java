package net.infstudio.infinitylib.api.remote.gui.node;

import net.infstudio.infinitylib.api.Pipeline;
import net.infstudio.infinitylib.api.remote.gui.Properties;
import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

/**
 * @author ci010
 */
public class DrawShape extends Gui implements DrawNode
{
	private Shape shape;
	private float[] cache = new float[6];

	public DrawShape(Shape shape)
	{
		this.shape = shape;
	}

	@Override
	public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties)
	{
		PathIterator pathIterator = shape.getPathIterator(new AffineTransform(0, 0, 0, 0, transform.x, transform.y));
		Tessellator instance = Tessellator.getInstance();
		WorldRenderer renderer = instance.getWorldRenderer();
		renderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		while (!pathIterator.isDone())
		{
			switch (pathIterator.currentSegment(cache))
			{
				case PathIterator.SEG_CLOSE:
					break;
				case PathIterator.SEG_MOVETO:
					renderer.pos(cache[0], cache[1], 0).endVertex();
					break;
				case PathIterator.SEG_LINETO:
					break;

				case PathIterator.SEG_QUADTO:
					break;
				case PathIterator.SEG_CUBICTO:
					break;
			}
			pathIterator.next();
		}
//		renderer.pos();//TODO
	}
}
