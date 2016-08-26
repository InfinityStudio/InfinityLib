package net.infstudio.infinitylib.common.support;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.LoadingDelegate;

import java.util.List;

/**
 * @author ci010
 */
@LoadingDelegate
public class SupportDelegate extends ASMRegistryDelegate<ModSupport>
{
	private List<ISupport> supports = Lists.newArrayList();

	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		try
		{
			if (!ISupport.class.isAssignableFrom(this.getAnnotatedClass()))
			{
				return;
			}
			ISupport support = (ISupport) this.getAnnotatedClass().newInstance();
			if (Loader.isModLoaded(support.getSupportModId()))
				supports.add(support);
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

	@Mod.EventHandler
	public void pre(FMLPreInitializationEvent event)
	{load(event);}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{load(event);}

	@Mod.EventHandler
	public void post(FMLPostInitializationEvent event)
	{load(event);}

	void load(FMLStateEvent event)
	{
//		for (ISupport support : supports)
//			support.onModLoad(LoaderStateUtil.getState(event.getClass()));
	}
}
