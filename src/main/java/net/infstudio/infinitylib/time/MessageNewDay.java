package net.infstudio.infinitylib.time;

import net.infstudio.infinitylib.api.network.AbstractClientMessage;
import net.infstudio.infinitylib.api.network.MessageCoder;
import net.infstudio.infinitylib.api.network.ModMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author ci010
 */
@ModMessage
public class MessageNewDay extends AbstractClientMessage<Void>
{
	public MessageNewDay()
	{
		super(MessageCoder.EMPTY);
	}

	@Override
	public IMessage handleClientMessage(EntityPlayer player, Void data, MessageContext ctx)
	{
		Hook.provider.getController().newDay();
		return null;
	}
}
