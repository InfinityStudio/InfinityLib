package net.infstudio.infinitylib.common.registry;

import net.infstudio.infinitylib.HelperMod;
import net.infstudio.infinitylib.api.registry.ModProxy;
import net.infstudio.infinitylib.api.Instance;
import net.infstudio.infinitylib.api.utils.TypeUtils;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.infstudio.infinitylib.common.DebugLogger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.utils.ASMDataUtil;
import net.infstudio.infinitylib.api.utils.PackageModIdMap;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author ci010
 */
public final class RegistryBufferManager
{
	private static RegistryBufferManager instance;

	public static RegistryBufferManager instance()
	{
		if (instance == null) instance = new RegistryBufferManager();
		return instance;
	}

	private Multimap<Class<? extends FMLStateEvent>, SubscriberInfo> subscriberInfoMap = HashMultimap.create();

	class SubscriberInfo
	{
		Object obj;
		Method method;

		public SubscriberInfo(Object obj, Method method)
		{
			this.obj = obj;
			this.method = method;
		}

		@Override
		public String toString()
		{
			return obj.getClass().getName().concat(" ").concat(method.getName());
		}
	}

	private PackageModIdMap rootMap = new PackageModIdMap();

	private class ClassKey
	{
		Class<?> clz;
		Class<?> genericType;

		ClassKey(Class<?> clz, Class<?> superClass)
		{
			this.clz = clz;
			this.genericType = superClass;
		}

		@Override
		public boolean equals(Object o)
		{
			if (o == null)
				return false;
			if (genericType == Object.class)
				return clz == o;
			else
				return genericType == o;
		}

		@Override
		public int hashCode()
		{
			if (genericType != Object.class)
				return genericType.hashCode();
			else
				return clz.hashCode();
		}
	}

	private void loadProxy(ASMDataTable table)
	{
		Map<ClassKey, Object> proxyClasses = Maps.newHashMap();
		Set<ASMDataTable.ASMData> proxyClassData = table.getAll(ModProxy.class.getName());
		for (ASMDataTable.ASMData data : proxyClassData)
		{
			ModProxy modProxy = ASMDataUtil.getAnnotation(data, ModProxy.class);
			if (modProxy.side() == HelperMod.proxy.getSide() || modProxy.side() == Side.SERVER)
			{
				Class<?> clz = ASMDataUtil.getClass(data);
				Optional<?> grab = Instance.Utils.grab(clz);
				if (grab.isPresent())
					proxyClasses.put(new ClassKey(clz, modProxy.genericType() == Void.class ? clz.getSuperclass()
							: modProxy.genericType()), grab.get());
				else
					proxyClasses.put(new ClassKey(clz, modProxy.genericType() == Void.class ? clz.getSuperclass()
							: modProxy.genericType()), clz);
			}
		}
		Set<ASMDataTable.ASMData> injectClass = table.getAll(ModProxy.Inject.class.getName());
		for (ASMDataTable.ASMData data : injectClass)
		{
			Field field = ASMDataUtil.getField(data);
			if (field == null)
			{
				return;
			}
			if (Modifier.isStatic(field.getModifiers()) && field.isAccessible())
			{
				Object o = proxyClasses.get(field.getType());
				if (o == null)
				{
					DebugLogger.fatal("Cannot find the proxy with type of {}", field.getType());
					return;
				}
				if (o instanceof Class)
				{
					Class clz = (Class) o;
					try
					{
						o = clz.newInstance();
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

				try
				{
					if (!field.isAccessible())
						field.setAccessible(true);
					field.set(null, o);
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public final void load(ASMDataTable table)
	{
		for (ASMDataTable.ASMData data : table.getAll(Mod.class.getName()))
			rootMap.put(ASMDataUtil.getClass(data).getPackage()
					.getName(), data.getAnnotationInfo().get("modid").toString());

		this.loadProxy(table);

		for (ASMDataTable.ASMData data : table.getAll(LoadingDelegate.class.getName()))
		{
			Class<?> registryDelegateType = ASMDataUtil.getClass(data);
			boolean asmType = ASMRegistryDelegate.class.isAssignableFrom(registryDelegateType);

//			if (asmType)
//				if (!(registryDelegateType.getGenericSuperclass() instanceof ParameterizedType))
//					throw new UnsupportedOperationException("The ASMRegistryDelegate class need to handle an " +
//							"annotation type");
			Optional<?> optional = Instance.Utils.grab(registryDelegateType);
			if (!optional.isPresent())
				return;
			Object delegate = optional.get();

			for (Method method : registryDelegateType.getDeclaredMethods())
			{
				if (!method.isAnnotationPresent(Mod.EventHandler.class))
					continue;

				if (method.getParameterTypes().length != 1)
					throw new UnsupportedOperationException("The method being subscribed need to have ONLY one parameter!");

				Class<?> methodParam = method.getParameterTypes()[0];
				if (!FMLStateEvent.class.isAssignableFrom(methodParam))
					throw new UnsupportedOperationException("The method's parameter must be a FMLStateEvent but not "
							.concat(methodParam.getName()));
				if (methodParam == FMLServerStoppedEvent.class && methodParam == FMLServerStoppingEvent.class)
					throw new UnsupportedOperationException("Not support FMLServerStoppedEvent and FMLServerStoppingEvent");

				Class<? extends FMLStateEvent> state = TypeUtils.cast(methodParam);
				if (asmType)
				{
					ASMRegistryDelegate asmDelegate = (ASMRegistryDelegate) delegate;
					Class<? extends Annotation> target = TypeUtils.getGenericTypeTo(asmDelegate);
					ArrayList<ASMDataTable.ASMData> list = Lists.newArrayList(table.getAll(target.getName()));
					Collections.sort(list, new Comparator<ASMDataTable.ASMData>()
					{
						@Override
						public int compare(ASMDataTable.ASMData o1, ASMDataTable.ASMData o2)
						{
							return o1.getClassName().compareTo(o2.getClassName());
						}
					});
					for (ASMDataTable.ASMData meta : list)
					{
						Package pkg = ASMDataUtil.getClass(meta).getPackage();
						String modid = rootMap.getModid(pkg.getName());
						if (modid == null)
						{
							modid = "helper";//TODO think about anonymous
							System.out.println("Anonymous for " + pkg.getName());
//							HelperMod.metadata.childMods.add();
						}
						asmDelegate.addCache(modid, meta);
					}
				}
				subscriberInfoMap.put(state, new SubscriberInfo(delegate, method));
			}
		}
	}

	public void invoke(FMLStateEvent state)
	{
		Class<? extends FMLStateEvent> realType = state.getClass();

		for (SubscriberInfo info : subscriberInfoMap.get(realType))
			try
			{
				if (info.obj instanceof ASMRegistryDelegate)
				{
					ASMRegistryDelegate asmRegistryDelegate = (ASMRegistryDelegate) info.obj;
					while (asmRegistryDelegate.hasNext())
					{
						asmRegistryDelegate.next();
						info.method.invoke(info.obj, state);
					}
				}
				else
					info.method.invoke(info.obj, state);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				System.out.println(info);
				e.printStackTrace();
			}

		if (state instanceof FMLLoadCompleteEvent)
		{
			rootMap = null;
			ASMDataUtil.clear();
			subscriberInfoMap.removeAll(FMLConstructionEvent.class);
			subscriberInfoMap.removeAll(FMLPreInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLPostInitializationEvent.class);
			subscriberInfoMap.removeAll(FMLLoadCompleteEvent.class);
		}
	}
}
