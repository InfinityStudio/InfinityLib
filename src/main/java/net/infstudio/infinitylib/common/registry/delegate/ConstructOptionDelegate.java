package net.infstudio.infinitylib.common.registry.delegate;

import net.infstudio.infinitylib.api.registry.components.Construct;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.infstudio.infinitylib.RegistryHelper;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.utils.TypeUtils;

import java.lang.annotation.Annotation;

/**
 * @author ci010
 */
@LoadingDelegate
public class ConstructOptionDelegate extends ASMRegistryDelegate<Construct.Option>
{
	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		try
		{
			RegistryHelper.INSTANCE.registerAnnotation(TypeUtils.<Annotation>cast(this.getAnnotatedClass()),
					this.getAnnotation().value().newInstance());
		}
		catch (InstantiationException e)
		{
			//TODO log that it need no parameter constructor.
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			if (this.getAnnotatedClass() == null)
				System.out.println("class null");
			if (this.getAnnotation() == null)
				System.out.println("annotaton null");
		}
	}
}
