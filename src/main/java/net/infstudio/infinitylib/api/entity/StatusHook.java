package net.infstudio.infinitylib.api.entity;

import net.infstudio.infinitylib.api.registry.ModProxy;
import net.infstudio.infinitylib.api.utils.Nullable;
import net.minecraft.entity.Entity;

/**
 * @author ci010
 */
public interface StatusHook
{
	@ModProxy.Inject
	StatusHook INSTANCE = null;

	@Nullable
	IStatus getStatus(Entity entity, String id);
}
