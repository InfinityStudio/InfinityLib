package net.infstudio.infinitylib.common.registry.delegate;

import net.infstudio.infinitylib.api.utils.Local;
import net.infstudio.infinitylib.api.registry.command.ISimpleCommand;
import net.infstudio.infinitylib.api.registry.command.ModCommand;
import net.infstudio.infinitylib.api.registry.ASMRegistryDelegate;
import net.infstudio.infinitylib.common.DebugLogger;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.infstudio.infinitylib.api.LoadingDelegate;

/**
 * @author ci010
 */
@LoadingDelegate
public class CommandDelegate extends ASMRegistryDelegate<ModCommand>
{
	@Mod.EventHandler
	public void onServerStart(FMLServerStartingEvent event)
	{
		try
		{
			final Object o = this.getAnnotatedClass().newInstance();
			ICommand cmd;
			if (o instanceof ICommand)
				cmd = (ICommand) o;
			else if (o instanceof ISimpleCommand)
				cmd = new CommandBase()
				{
					@Override
					public String getCommandName()
					{
						return ((ISimpleCommand) o).name();
					}

					@Override
					public String getCommandUsage(ICommandSender sender)
					{
						return "commands.".concat(this.getCommandName()).concat(".usage");
					}

					@Override
					public void processCommand(ICommandSender sender, String[] args) throws CommandException
					{
						((ISimpleCommand) o).processCommand(sender, args);
					}
				};
			else
			{
				return;
			}
			final String commandUsage = cmd.getCommandUsage(null);
			if (commandUsage != null)
				Local.trans(commandUsage);
			DebugLogger.info("Register the command [/{}] <- [{}:{}].", cmd.getCommandName(),
					this.getModid(), this.getAnnotatedClass());
			((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand(cmd);
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
