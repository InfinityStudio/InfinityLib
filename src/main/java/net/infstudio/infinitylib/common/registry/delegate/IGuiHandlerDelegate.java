package net.infstudio.infinitylib.common.registry.delegate;

import net.infstudio.infinitylib.HelperMod;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.registry.ModGuiHandler;

/**
 * @author ci010
 */
@LoadingDelegate
public class IGuiHandlerDelegate extends ASMRegistryDelegate<ModGuiHandler>
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		try
		{
			ModContainer modContainer = Loader.instance().getIndexedModList().get(this.getModid());
			Object mod;
			if (modContainer == null)
				mod = HelperMod.instance;
			else mod = modContainer.getMod();
			NetworkRegistry.INSTANCE.registerGuiHandler(mod, (IGuiHandler) this.getAnnotatedClass().newInstance());
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
