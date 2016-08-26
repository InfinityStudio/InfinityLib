package net.infstudio.infinitylib.command;

import net.infstudio.infinitylib.api.registry.command.ModCommand;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandGameMode;

import java.util.List;

/**
 * @author ci010
 */
@ModCommand
public class CommandGM extends CommandGameMode
{
	@Override
	public List getCommandAliases()
	{
		return Lists.newArrayList("gm");
	}
}
