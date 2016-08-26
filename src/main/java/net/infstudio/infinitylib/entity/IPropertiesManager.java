package net.infstudio.infinitylib.entity;

import net.infstudio.infinitylib.HelperMod;
import net.infstudio.infinitylib.api.Instance;
import net.infstudio.infinitylib.api.entity.StatusHook;
import net.infstudio.infinitylib.api.registry.ModHandler;
import net.infstudio.infinitylib.api.registry.ModProxy;
import net.infstudio.infinitylib.api.utils.Nullable;
import net.infstudio.infinitylib.api.entity.EntityHandler;
import net.infstudio.infinitylib.api.entity.IStatus;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author ci010
 */
@ModHandler
@ModProxy(side = Side.SERVER, genericType = StatusHook.class)
public class IPropertiesManager implements ITickable, StatusHook
{
	@Instance(weak = true)
	private static IPropertiesManager instance;

	private ArrayList<EntityHandler> handlers = Lists.newArrayList();
	private WeakHashMap<Entity, ITickable> updateWeakHashMap = new WeakHashMap<Entity, ITickable>();
	private StatusCollection collection = new StatusCollection(updateWeakHashMap);

	public void registerStatus(EntityHandler property)
	{
		HelperMod.LOG.info("Register EntityHandler: [{}]", property.getClass());
		handlers.add(property);
	}

	public static boolean enable()
	{
		return instance != null;
	}

	public static IPropertiesManager instance()
	{
		if (instance == null)
			instance = new IPropertiesManager();
		return instance;
	}

	@Nullable
	public IExtendedEntityProperties get(Entity entity, String id)
	{
		return entity.getExtendedProperties(id);
	}

	@Nullable
	@Override
	public IStatus getStatus(Entity entity, String id)
	{
		IExtendedEntityProperties p = get(entity, id);
		if (p instanceof Status)
			return ((Status) p).real;
		return null;
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)
	{
		for (EntityHandler handler : this.handlers)
		{
			collection.start(event.entity);
			handler.handle(event.entity, collection);
			collection.end();
		}
	}

	@Override
	public void update()
	{
		for (Map.Entry<Entity, ITickable> entry : this.updateWeakHashMap.entrySet())
			if (entry.getKey().isEntityAlive())
				entry.getValue().update();
			else updateWeakHashMap.remove(entry.getKey());
	}
}
