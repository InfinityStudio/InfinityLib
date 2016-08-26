package net.infstudio.infinitylib.common.registry.delegate;

import net.infstudio.infinitylib.HelperMod;
import net.infstudio.infinitylib.api.utils.TypeUtils;
import com.google.common.collect.Maps;
import net.infstudio.infinitylib.common.DebugLogger;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.infstudio.infinitylib.api.utils.Local;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.registry.ModEntity;
import net.infstudio.infinitylib.api.utils.FMLLoadingUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author ci010
 */
@LoadingDelegate
public class EntityRegistryDelegate extends ASMRegistryDelegate<ModEntity>
{
	static Map<String, Integer> modidCache = Maps.newHashMap();

	static int nextId(String modid)
	{
		int next = 0;
		if (!modidCache.containsKey(modid))
			modidCache.put(modid, 0);
		else
		{
			next = modidCache.get(modid) + 1;
			modidCache.put(modid, next);
		}
		return next + 128;
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		String modid = this.getModid();
		ModEntity anno = this.getAnnotation();
		Class<? extends net.minecraft.entity.Entity> clz = TypeUtils.cast(this.getAnnotatedClass());
		String info = "Register Entity: [{}] <- [{}]| id <- {}";
		String name = anno.name();
		System.out.println(modid);
		Object mod = FMLLoadingUtil.getModContainer(modid).getMod();
		if (name.equals(""))
			name = clz.getSimpleName();
		int id = anno.id();
		if (id == -1)
			id = nextId(modid);
		ModEntity.Spawner spawner = this.getAnnotatedClass().getAnnotation(ModEntity.Spawner.class);
		if (spawner != null)
		{
			info = info.concat(" | egg");
			EntityRegistry.registerModEntity(clz, name, id, mod, anno.trackingRange(), anno
					.updateFrequency(), anno.sendsVelocityUpdates(), spawner.primaryColor(), spawner.secondaryColor());
			Local.trans("entity.".concat(modid).concat(".").concat(name).concat(".name"));
		}
		else
		{
			EntityRegistry.registerModEntity(clz, name, id, mod, anno.trackingRange(), anno
					.updateFrequency(), anno.sendsVelocityUpdates());
		}

		if (HelperMod.proxy.isClient())
		{
			if (clz.isAnnotationPresent(ModEntity.Render.class))
			{
				Class<? extends Render> render = clz.getAnnotation(ModEntity.Render.class).value();
				Render renderObj;
				try
				{
					renderObj = render.newInstance();
				}
				catch (InstantiationException e)
				{
					renderObj = this.handleException(render, name);
				}
				catch (IllegalAccessException e)
				{
					renderObj = this.handleException(render, name);
				}
				if (renderObj != null)
				{
					RenderingRegistry.registerEntityRenderingHandler(clz, renderObj);
					info = info.concat(" | render <- {}");
					DebugLogger.info(info, name, this.getModid(), id, render.getSimpleName());
				}
				else
					DebugLogger.info(info, name, this.getModid(), id);
			}
			else
				DebugLogger.info(info, name, this.getModid(), id);
		}
	}

	private Render handleException(Class<? extends Render> render, String name)
	{
		try
		{
			Constructor constructor = render.getDeclaredConstructor();
			constructor.setAccessible(true);
			return (Render) constructor.newInstance();
		}
		catch (NoSuchMethodException e1)
		{
			try
			{
				Constructor constructor = render.getConstructor();
				constructor.setAccessible(true);
				return (Render) constructor.newInstance();
			}
			catch (NoSuchMethodException e)
			{
				HelperMod.LOG.fatal("Cannot create the instance of {}'s renderer. There should be a " +
						"constructor without any parameter for the renderer class. The entity {} won't be register a " +
						"renderer.", name, name);
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}

		}
		catch (IllegalAccessException e1)
		{
			e1.printStackTrace();
		}
		catch (InstantiationException e1)
		{
			e1.printStackTrace();
		}
		catch (InvocationTargetException e1)
		{
			e1.printStackTrace();
		}
		return null;
	}
}
