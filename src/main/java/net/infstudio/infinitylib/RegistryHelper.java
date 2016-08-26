package net.infstudio.infinitylib;

import net.infstudio.infinitylib.api.registry.components.ArgumentHelper;
import net.infstudio.infinitylib.api.sitting.Sitable;
import net.infstudio.infinitylib.api.utils.FMLLoadingUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.infstudio.infinitylib.common.registry.ContainerMeta;
import net.infstudio.infinitylib.common.registry.RegBlock;
import net.infstudio.infinitylib.common.registry.RegItem;
import net.infstudio.infinitylib.sitting.SitHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.ModContainer;
import net.infstudio.infinitylib.common.registry.Namespace;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class handles most block/item create/register things.
 */
public enum RegistryHelper
{
	INSTANCE;

	private Map<String, ContainerMeta> containerIdx = Maps.newHashMap();

	private Map<Class<? extends Annotation>, ArgumentHelper> annoMap = Maps.newHashMap();

	void track(ContainerMeta meta)
	{
		this.containerIdx.put(meta.modid, meta);
	}

	private String currentModid;
	ModContainer container;

	public void start(ContainerMeta meta)
	{
		FMLLoadingUtil.setActiveContainer(FMLLoadingUtil.getModContainer(currentModid = meta.modid));
	}

	public void end()
	{
		FMLLoadingUtil.setActiveContainer(container);
		currentModid = container.getModId();
	}

	public String currentMod()
	{
		return this.currentModid;
	}

	public void setLang(String modid, String... lang)
	{
		if (lang == null || lang.length == 0 || lang[0].equals(""))
			lang = new String[]
					{"zh_CN", "en_US"};
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).lang(true).langType(lang));
		else
			this.containerIdx.get(modid).lang(true).langType(lang);
	}

	public void setModel(String modid)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).model(true));
		else
			this.containerIdx.get(modid).model(true);
	}

	public void registerMod(String modid, Class<?> container)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addRawContainer(container));
		else
			this.containerIdx.get(modid).addRawContainer(container);
	}

	public void register(String modid, ImmutableSet set)
	{
		if (!this.containerIdx.containsKey(modid))
			this.track(new ContainerMeta(modid).addUnregistered(set));
		else
			this.containerIdx.get(modid).addUnregistered(set);
	}

	public void registerBlock(String modid, Block block, String name)
	{
		this.registerBlock(modid, block, name, null);
	}

	public void registerBlock(String modid, Block block, String name, String ore)
	{
		ImmutableSet temp = ImmutableSet.of(new Namespace(name,
				new RegBlock(block)).setOreName(ore));
		this.register(modid, temp);
	}

	public void registerItem(String modid, Item item, String name)
	{
		this.registerItem(modid, item, name, null);
	}

	public void registerItem(String modid, Item item, String name, String ore)
	{
		ImmutableSet temp = ImmutableSet.of(new Namespace(name,
				new RegItem(item)).setOreName(ore));
		this.register(modid, temp);
	}


	public Map<Class<? extends Annotation>, ArgumentHelper> getAnnotationMap()
	{
		return ImmutableMap.copyOf(this.annoMap);
	}

	public Iterator<ContainerMeta> getRegistryInfo()
	{
		return this.containerIdx.values().iterator();
	}

	public Set<Field> parseContainer(Class<?> container)
	{//TODO check permission
		Set<Field> temp = Sets.newHashSet();
		for (Field f : container.getFields())
			if (Modifier.isStatic(f.getModifiers()))
				temp.add(f);
			else
				HelperMod.LOG.info("The field {} in container {} is not static so that it won'registerInit be constructed and registered",
						f.getName(),
						container.getName());
		return temp;
	}

	/**
	 * Make the block sittable
	 *
	 * @param block
	 */
	public void registerSittableBlock(final Block block)
	{
		SitHandler.register(new Sitable()
		{
			@Override
			public Block sitableBlock()
			{
				return block;
			}

			@Override
			public Situation getSituation()
			{
				return Sitable.DEFAULT;
			}
		});
	}

//	/**
//	 * Register a new message with its handler class.
//	 * <p>Highly recommend to use {@link ModMessage} to register this.</p>
//	 *
//	 * @param handlerClass The handler class which binds with message class
//	 * @param messageClass The message class
//	 * @param <Message>    The type of message
//	 */
//	public <Message extends IMessage> void registerMessage(Class<? extends AbstractMessage<Message>>
//																   handlerClass, Class<Message> messageClass)
//	{
//		ModNetwork.instance().registerMessage(handlerClass, messageClass);
//	}

	/**
	 * Register custom annotation for constructing object.
	 * <p/>
	 * See {@link Construct.Float#value()} with
	 * {@link Construct.FloatHelper#getArguments(Annotation)}
	 *
	 * @param annotation The annotation used to catch constructing arguments.
	 * @param helper     The helper will handle the annotation above.
	 */
	public void registerAnnotation(Class<? extends Annotation> annotation, ArgumentHelper helper)
	{
		if (!annoMap.containsKey(annotation))
			annoMap.put(annotation, helper);
		else
			throw new IllegalArgumentException("The annotation has already been registerd!");
	}

	/**
	 * Register containers without both language files and model files.
	 * <p/>
	 * See {@link RegistryHelper#register(boolean, boolean, Class[])}
	 *
	 * @param containers The item/block/custom containers.
	 */
	public void register(Class<?>... containers)
	{
		register(false, false, containers);
	}

	/**
	 * Register containers. All the static fields with type assigning from
	 * {@link Item}/ {@link Block}/{@link ComponentsReference} in these containers
	 * will be registered.
	 *
	 * @param ifGenerateLang  If your mod need to generate language files
	 * @param ifGenerateModel If your mod need to generate model files
	 * @param containers      The item/block/custom containers.
	 */
	public void register(boolean ifGenerateLang, boolean ifGenerateModel, Class<?>... containers)
	{//// TODO: 2016/1/2 check if this mod is registered. 
		String modid = Loader.instance().activeModContainer().getModId();
		ContainerMeta meta = new ContainerMeta(modid).lang(ifGenerateLang).model(ifGenerateModel);
		for (Class<?> container : containers)
			meta.addRawContainer(container);
		this.track(meta);
	}

	public void close()
	{
		if (Loader.instance().getLoaderState() == LoaderState.AVAILABLE)
		{
			this.containerIdx = null;
			this.annoMap = null;
		}
	}
}
