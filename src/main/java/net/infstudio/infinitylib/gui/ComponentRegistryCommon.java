package net.infstudio.infinitylib.gui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.infstudio.infinitylib.api.Pipeline;
import net.infstudio.infinitylib.api.registry.ModProxy;
import net.infstudio.infinitylib.api.remote.gui.ComponentAPI;
import net.infstudio.infinitylib.api.remote.gui.ComponentRepository;
import net.infstudio.infinitylib.api.remote.gui.Properties;
import net.infstudio.infinitylib.api.remote.gui.components.GuiComponent;
import net.infstudio.infinitylib.api.remote.gui.node.DrawNode;
import net.infstudio.infinitylib.api.seril.IJsonSerializer;
import net.infstudio.infinitylib.api.utils.TypeUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
@ModProxy(side = Side.SERVER, genericType = ComponentRepository.class)
public class ComponentRegistryCommon implements ComponentRepository
{
	private static Set<Class<?>> supportedType = Sets.newHashSet(new Class<?>[]{int.class, float.class,
																				short.class, long.class,
																				double.class, boolean.class,
																				char.class, Float.class, Long.class,
																				Character.class, Double.class,
																				Short.class, String.class,
																				Integer.class, Boolean.class,
																				Enum.class});
	private BiMap<ResourceLocation, DrawNode> map = HashBiMap.create();
	private Map<ResourceLocation, Properties.Key> propMap = Maps.newHashMap();
	private Map<Class, IJsonSerializer> serializableMap = Maps.newHashMap();

	protected void register()
	{
		registerDrawNode(ComponentAPI.LOC_DRAW_TEXTURE, null);
		registerDrawNode(ComponentAPI.LOC_DRAW_BACKGROUND, null);
		registerDrawNode(ComponentAPI.LOC_DRAW_BORDER_TEXTS, null);
		registerDrawNode(ComponentAPI.LOC_DRAW_STRING, null);

		registerDrawNode(ComponentAPI.LOC_ANIM_FADE_IN, null);
		registerDrawNode(ComponentAPI.LOC_ANIM_FADE_IN, null);

		registerDrawNode(new ResourceLocation("draw:pre"), null);
		registerDrawNode(new ResourceLocation("draw:post"), null);
	}

	public DrawNode fetchNode(ResourceLocation location)
	{
		return map.get(location);
	}

	@Override
	public <T> Properties.Key<T> fetchKey(DrawNode node, Class<T> type)
	{
		return fetchKey(map.inverse().get(node), type);
	}

	@Override
	public <T> Properties.Key<T> fetchKey(ResourceLocation location, Class<T> type)
	{
		return TypeUtils.cast(propMap.get(new ResourceLocation(location.toString().concat(".")
				.concat(type.getSimpleName().toLowerCase()))));
	}

	@Override
	public Properties newProperty()
	{
		return null;
//		TODO return new PropertiesImpl();
	}

	@Override
	public Pipeline<DrawNode> newDrawPipe()
	{
		return null;
	}

	public void registerDrawNode(ResourceLocation location, DrawNode drawable)
	{
		if (map.containsKey(location))
			throw new IllegalArgumentException("duplicate key");
		if (this.getClass() == ComponentRegistryCommon.class)
			map.put(location, new ClientElement(location));
		else
			map.put(location, drawable);
	}

	public <T> void registerSerializer(IJsonSerializer<T> serializer, Class<T> type)
	{
		serializableMap.put(type, serializer);
	}

	class ClientElement implements DrawNode
	{
		ResourceLocation loc;

		public ClientElement(ResourceLocation name)
		{
			this.loc = name;
		}
		@Override
		public void draw(GuiComponent.Transform transform, Pipeline<DrawNode> pipeline, Properties properties) {

		}
	}
}
