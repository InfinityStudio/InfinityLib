package net.infstudio.infinitylib.common.registry.delegate;

import net.infstudio.infinitylib.HelperMod;
import net.infstudio.infinitylib.RegistryHelper;
import net.infstudio.infinitylib.api.registry.components.ComponentStruct;
import net.infstudio.infinitylib.common.registry.NamespaceMakerSimple;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.registry.components.ModComponent;

/**
 * @author ci010
 */
@LoadingDelegate
public class ModComponentDelegate extends ASMRegistryDelegate<ModComponent>
{
	private NamespaceMakerSimple simpleMaker = new NamespaceMakerSimple().staticSensitve(false);

	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		String name = this.getAnnotation().name().equals("") ?
				this.getAnnotatedClass().getSimpleName() :
				this.getAnnotation().name();
		try
		{
			if (Item.class.isAssignableFrom(this.getAnnotatedClass()))
				RegistryHelper.INSTANCE.registerItem(this.getModid(), (Item) this.getAnnotatedClass().newInstance(), name);
			else if (Block.class.isAssignableFrom(this.getAnnotatedClass()))
				RegistryHelper.INSTANCE.registerBlock(this.getModid(), (Block) this.getAnnotatedClass().newInstance(), name);
			else if (this.getAnnotatedClass().isAnnotationPresent(ComponentStruct.class))
				RegistryHelper.INSTANCE.register(this.getModid(), simpleMaker.make(this.getAnnotatedClass().newInstance()));
			else
				HelperMod.LOG.warn("The class {} is neither a block nor an item! Moreover, it not a ComponentStruct. " +
						"It will not be registered!", this
						.getAnnotatedClass().getName());
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
