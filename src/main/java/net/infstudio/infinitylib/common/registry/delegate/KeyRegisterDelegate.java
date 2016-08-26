package net.infstudio.infinitylib.common.registry.delegate;

import net.infstudio.infinitylib.api.utils.TypeUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.infstudio.infinitylib.common.registry.KeyBindingHandler;
import net.infstudio.infinitylib.common.registry.abstracts.KeyPair;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.registry.key.KeyHandler;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.registry.key.ModKeyBinding;

/**
 * @author ci010
 */
@LoadingDelegate
public class KeyRegisterDelegate extends ASMRegistryDelegate<ModKeyBinding>
{
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModKeyBinding anno = this.getAnnotation();
		Class<?> clz = this.getAnnotatedClass();
		if (!KeyHandler.class.isAssignableFrom(clz))
		{
			//// TODO: 2016/1/5 Log
			return;
		}
		else
		{
			try
			{
				final KeyHandler handler = TypeUtils.cast(this.getAnnotatedClass().newInstance());
				KeyBindingHandler.add(
						new KeyPair(anno.id(), anno.keyCode())
						{
							@Override
							public void onKeyPressed()
							{
								handler.onKeyPressed(this.getKeyBinding());
							}
						});
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		KeyBindingHandler.buildList();
	}
}
