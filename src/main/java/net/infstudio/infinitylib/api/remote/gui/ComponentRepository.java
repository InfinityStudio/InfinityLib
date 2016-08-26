package net.infstudio.infinitylib.api.remote.gui;

import net.infstudio.infinitylib.api.Pipeline;
import net.infstudio.infinitylib.api.registry.ModProxy;
import net.infstudio.infinitylib.api.remote.gui.node.DrawNode;
import net.minecraft.util.ResourceLocation;

/**
 * @author ci010
 */
public interface ComponentRepository
{
	@ModProxy.Inject
	ComponentRepository repository = null;

	DrawNode fetchNode(ResourceLocation location);

	<T> Properties.Key<T> fetchKey(DrawNode node, Class<T> type);

	<T> Properties.Key<T> fetchKey(ResourceLocation location, Class<T> type);

	Properties newProperty();

	Pipeline<DrawNode> newDrawPipe();
}
