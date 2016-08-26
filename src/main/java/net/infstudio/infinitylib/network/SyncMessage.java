package net.infstudio.infinitylib.network;

import net.infstudio.infinitylib.api.remote.container.ContainerBase;
import net.infstudio.infinitylib.api.network.AbstractClientMessage;
import net.infstudio.infinitylib.api.network.ModMessage;
import net.infstudio.infinitylib.api.network.NBTCoder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.infstudio.infinitylib.api.seril.ITagSerializable;

/**
 * @author ci010
 */
@ModMessage
public class SyncMessage extends AbstractClientMessage<NBTTagCompound>
{
	public SyncMessage()//TODO use queue to send this message
	{super(new NBTCoder());}

	public SyncMessage(int windowId, int id, ITagSerializable seril)
	{
		super(new NBTCoder());
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound data = new NBTTagCompound();
		seril.writeToNBT(data);
		tag.setTag("data", data);
		tag.setInteger("id", id);
		tag.setInteger("windowId", windowId);
		this.delegate.set(tag);
	}

	@Override
	public IMessage handleClientMessage(EntityPlayer player, NBTTagCompound data, MessageContext ctx)
	{
//		if(SyncHub)//TODO finish
		if (player.openContainer != null && player.openContainer.windowId == data.getInteger("windowId") &&
				player.openContainer instanceof ContainerBase)
			((ContainerBase) player.openContainer).load(data.getInteger("id"), data.getCompoundTag("data"));
		return null;
	}
}
