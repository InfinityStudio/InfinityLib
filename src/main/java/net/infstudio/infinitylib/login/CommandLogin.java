package net.infstudio.infinitylib.login;

import net.infstudio.infinitylib.api.registry.command.ISimpleCommand;
import net.infstudio.infinitylib.api.registry.command.ModCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author ci010
 */
@ModCommand
public class CommandLogin implements ISimpleCommand
{
	@Override
	public String name()
	{
		return "login";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws UsageException
	{
		if (sender instanceof EntityPlayer)
		{
			if (args.length == 1)
				LoginSystem.getInstance().login((EntityPlayer) sender, args[0]);
			else
				throw new UsageException(this.name());
		}
	}
}
