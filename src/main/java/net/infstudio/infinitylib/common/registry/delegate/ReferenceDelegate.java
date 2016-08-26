package net.infstudio.infinitylib.common.registry.delegate;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.infstudio.infinitylib.RegistryHelper;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.registry.components.ComponentsReference;

/**
 * @author ci010
 */
@LoadingDelegate
public class ReferenceDelegate extends ASMRegistryDelegate<ComponentsReference>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		RegistryHelper.INSTANCE.registerMod(this.getModid(), this.getAnnotatedClass());
	}
}
