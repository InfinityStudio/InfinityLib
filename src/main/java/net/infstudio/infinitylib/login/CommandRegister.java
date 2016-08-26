package net.infstudio.infinitylib.login;

import net.infstudio.infinitylib.api.registry.command.ISimpleCommand;
import net.infstudio.infinitylib.api.registry.command.ModCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ci010
 */
@ModCommand
public class CommandRegister implements ISimpleCommand
{
	@Override
	public String name()
	{
		return "register";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws UsageException
	{
		if (sender instanceof EntityPlayer)
		{
			if (args.length == 2)
				LoginSystem.getInstance().register((EntityPlayer) sender, args);
			else
				throw new UsageException(this.name());
		}
	}
}
