package net.infstudio.infinitylib.common.registry.delegate;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.infstudio.infinitylib.api.LoadingDelegate;
import net.infstudio.infinitylib.api.network.ModMessage;
import net.infstudio.infinitylib.api.network.ModNetwork;

/**
 * @author ci010
 */
@LoadingDelegate
public class PacketRegistryDelegate extends ASMRegistryDelegate<ModMessage>
{
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (IMessageHandler.class.isAssignableFrom(this.getAnnotatedClass()))
			try
			{
				IMessageHandler msg = (IMessageHandler) this.getAnnotatedClass().newInstance();
				ModNetwork.instance().registerMessage(msg);
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
