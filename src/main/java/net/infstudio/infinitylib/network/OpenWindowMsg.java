package net.infstudio.infinitylib.network;

import net.infstudio.infinitylib.api.network.AbstractClientMessage;
import net.infstudio.infinitylib.api.network.NBTCoder;
import net.infstudio.infinitylib.api.vars.VarSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

/**
 * @author ci010
 */
public class OpenWindowMsg extends AbstractClientMessage<NBTTagCompound>
{
	public OpenWindowMsg() {super(new NBTCoder());}

	public OpenWindowMsg(String json, int cacheTime, List<VarSync> syncList)
	{
		this();

	}

	@Override
	public IMessage handleClientMessage(EntityPlayer player, NBTTagCompound data, MessageContext ctx)
	{
		return null;
	}
}
