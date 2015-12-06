package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.HelperMod;
import net.ci010.minecrafthelper.RegistryHelper;
import net.ci010.minecrafthelper.abstracts.ArgumentHelper;
import net.ci010.minecrafthelper.abstracts.BlockItemStruct;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.field.OreDic;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.BlockItemContainer;
import net.ci010.minecrafthelper.core.FileGenerator;
import net.ci010.minecrafthelper.core.Maker;
import net.ci010.minecrafthelper.core.MakerStruct;
import net.ci010.minecrafthelper.data.ContainerMeta;
import net.ci010.minecrafthelper.data.StructBlock;
import net.ci010.minecrafthelper.data.StructItem;
import net.ci010.minecrafthelper.util.FMLModUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ci010
 */
@ASMDelegate
public class BlockItemRegistryDelegate extends RegistryDelegate<BlockItemContainer>
{
	private Maker<Block, BlockItemStruct> blockMaker;
	private Maker<Item, BlockItemStruct> itemMaker;
	private Maker<BlockItemStruct, BlockItemStruct> maker;

	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		RegistryHelper.INSTANCE.registerMod(this.getModid(), this.getAnnotatedClass());
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Map<Class<? extends Annotation>, ArgumentHelper> temp = RegistryHelper.INSTANCE.getAnnotationMap();
		blockMaker = new MakerStruct<Block>(temp)
		{
			@Override
			protected BlockItemStruct warpStruct(Block target)
			{
				return new StructBlock(target);
			}
		};
		itemMaker = new MakerStruct<Item>(temp)
		{
			@Override
			protected BlockItemStruct warpStruct(Item target)
			{
				return new StructItem(target);
			}
		};
		maker = new MakerStruct<BlockItemStruct>(temp)
		{
			@Override
			protected BlockItemStruct warpStruct(BlockItemStruct target)
			{
				return target;
			}
		};
		ModContainer theMod = Loader.instance().activeModContainer();
		Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
		while (itr.hasNext())
		{
			ContainerMeta meta = itr.next();
			FMLModUtil.setActiveContainer(FMLModUtil.getModContainer(meta.modid));
			System.out.println("start " + meta.modid);
			this.collect(meta);
			FMLModUtil.setActiveContainer(theMod);
		}
	}

	private void collect(ContainerMeta meta)
	{
		for (Field f : meta.getFields())
		{
			BlockItemStruct data = this.parseField(f);
			if (data == null)//TODO log
				continue;
			StringBuilder builder = new StringBuilder(f.getName());
			for (int i = 0; i < builder.length(); ++i)
				if (Character.isUpperCase(builder.charAt(i)))
				{
					builder.setCharAt(i, Character.toLowerCase(builder.charAt(i)));
					builder.insert(i - 1, ".");
				}
			String name = builder.toString();
			String ore = null;
			OreDic anno = f.getAnnotation(OreDic.class);
			if (anno != null)
				ore = anno.value().equals("") ? null : anno.value();
			meta.addUnregistered(data, name, ore);
		}
	}

	private void build(ContainerMeta meta)
	{
		for (ContainerMeta.BasicInfo basicInfo : meta.getUnregistered())
		{
			basicInfo.struct.setName(basicInfo.name);
			if (basicInfo.struct.blocks() != null)
				for (Block block : basicInfo.struct.blocks())
				{
					GameRegistry.registerBlock(block, basicInfo.name);
					if (basicInfo.needOre)
						OreDictionary.registerOre(basicInfo.oreDicName == null ? block.getUnlocalizedName().substring(5) :
								basicInfo.oreDicName, block);
				}
			if (basicInfo.struct.items() != null)
				for (Item item : basicInfo.struct.items())
				{
					GameRegistry.registerItem(item, basicInfo.name);
					if (basicInfo.needOre)
						OreDictionary.registerOre(basicInfo.oreDicName == null ? item.getUnlocalizedName().substring(5) :
								basicInfo.oreDicName, item);
				}
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		ModContainer theMod = Loader.instance().activeModContainer();

		{
			Iterator<ContainerMeta> itr = RegistryHelper.INSTANCE.getRegistryInfo();
			while (itr.hasNext())
			{
				ContainerMeta meta = itr.next();
				FMLModUtil.setActiveContainer(FMLModUtil.getModContainer(meta.modid));
				this.build(meta);
				if (HelperMod.proxy.isClient())
					for (ContainerMeta.BasicInfo basicInfo : meta.getUnregistered())
					{
						BlockItemStruct data = basicInfo.struct;
						if (data.blocks() != null)
							for (Block block : data.blocks())
								if (meta.getBlockModelHandler() == null || !meta.getBlockModelHandler().handle(block))
									this.registerModel(Item.getItemFromBlock(block), meta.modid, block.getUnlocalizedName()
											.substring(5).replace(".", "_"));
						if (data.items() != null)
							for (Item item : data.items())
								if (meta.getItemModelHandler() == null || !meta.getItemModelHandler().handle(item))
									this.registerModel(item, meta.modid, item.getUnlocalizedName().substring(5).replace(".", "_"));
						if (meta.needModel() || meta.needLang())
						{
							FileGenerator g = new FileGenerator(meta.modid);
							if (meta.needModel())
								try
								{
									g.model(data);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
							if (meta.needLang())
							{
								g.setLangType(meta.langType());
								g.lang(data);
								try
								{
									g.writeLang();
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
							}
						}
					}
				FMLModUtil.setActiveContainer(theMod);
			}
		}
	}

	BlockItemStruct parseField(Field f)
	{
		Class c = f.getType();
		if (Item.class.isAssignableFrom(c))
			return itemMaker.make(f);
		if (Block.class.isAssignableFrom(c))
			return blockMaker.make(f);
		if (BlockItemStruct.class.isAssignableFrom(c))
			return maker.make(f);
		return null;
	}

	@SideOnly(Side.CLIENT)
	public void registerModel(Item target, String modId, String... name)
	{
		int index = 0;
		for (String sub : name)
		{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(target,
					index++,
					new ModelResourceLocation(modId +
							":" +
							sub, "inventory"));
			ModelBakery.addVariantName(target, modId + ":" + sub);
		}
	}
}
