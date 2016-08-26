package net.infstudio.infinitylib.client;

import net.infstudio.infinitylib.CommonProxy;
import net.infstudio.infinitylib.api.utils.Local;
import net.infstudio.infinitylib.api.utils.Environment;
import net.infstudio.infinitylib.common.registry.ContainerMeta;
import net.infstudio.infinitylib.common.registry.LanguageReporter;
import net.minecraftforge.fml.relauncher.Side;
import net.infstudio.infinitylib.api.utils.FileReference;
import net.infstudio.infinitylib.common.registry.Namespace;
import net.infstudio.infinitylib.api.utils.NameFormatter;

public class ClientProxy extends CommonProxy
{
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}

	@Override
	protected void register(ContainerMeta meta)
	{
		super.register(meta);
		for (Namespace namespace : meta.getUnregistered())
			if (meta.getModelHandler() == null || !meta.getModelHandler().handle(namespace.getComponent()))
				namespace.getComponent().registerModel(NameFormatter.upperTo_(namespace.toString()));
		if (Environment.debug())
		{
			FileReference.registerFile("all");
			FileReference.registerFile(meta.modid);
			LanguageReporter.instance().start(meta.modid, meta.langType());
			for (Namespace namespace : meta.getUnregistered())
				Local.trans(namespace.getComponent().getUnlocalizedName());
			LanguageReporter.instance().end();
		}
	}

	@Override
	public boolean isClient()
	{
		return true;
	}
}
