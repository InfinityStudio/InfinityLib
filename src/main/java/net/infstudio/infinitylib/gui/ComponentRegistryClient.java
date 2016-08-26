package net.infstudio.infinitylib.gui;

import net.infstudio.infinitylib.api.registry.ModProxy;
import net.infstudio.infinitylib.api.remote.gui.ComponentRepository;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author ci010
 */
@ModProxy(side = Side.CLIENT, genericType = ComponentRepository.class)
public class ComponentRegistryClient extends ComponentRegistryCommon
{
	@Override
	protected void register()
	{
//		TODO WTF
//		registerDrawNode(ComponentAPI.LOC_DRAW_TEXTURE, DrawTexture.INSTANCE);
//		registerDrawNode(ComponentAPI.LOC_DRAW_BACKGROUND, DrawerDefaultBackground.INSTANCE);
//		registerDrawNode(ComponentAPI.LOC_DRAW_BORDER_TEXTS, DrawBorderTexts.INSTANCE);
//		registerDrawNode(ComponentAPI.LOC_DRAW_STRING, DrawString.INSTANCE);
//
//		registerDrawNode(ComponentAPI.LOC_ANIM_FADE_IN, AnimationFadeOut.INSTANCE);
//		registerDrawNode(ComponentAPI.LOC_ANIM_FADE_IN, AnimationFadeIn.INSTANCE);
//
//		registerDrawNode(new ResourceLocation("draw:pre"), new DrawNode()
//		{
//		});
//		registerDrawNode(new ResourceLocation("draw:post"), new DrawNode()
//		{
//			@Override
//			public void draw(int x, int y, Pipeline<DrawNode> pipeline, Properties properties)
//			{
//				GL11.glPopMatrix();
//			}
//		});
	}
}
