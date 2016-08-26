package net.infstudio.infinitylib.common.registry;

import net.infstudio.infinitylib.common.registry.abstracts.RegComponentBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;


/**
 * @author ci010
 */
public class Namespace
{
	private String parent = null, name, oreName;
	private RegComponentBase component;

	public Namespace(String name, RegComponentBase component)
	{
		this.component = component;
		this.name = name;
	}

	public Namespace setParent(String parent)
	{
		this.parent = parent;
		return this;
	}

	public Namespace setOreName(String name)
	{
		oreName = name;
		return this;
	}

	public boolean needRegOre()
	{
		return oreName != null;
	}

	public String getOreName()
	{
		return oreName;
	}

	public String getParent()
	{
		return parent;
	}

	public String getName()
	{
		return name;
	}

	public RegComponentBase getComponent()
	{
		return component;
	}

	@Override
	public String toString()
	{
		return parent == null ? name : parent.concat("_").concat(name);
	}

	public static Namespace newSpace(String name, Object o)
	{
		if (o instanceof Block)
			return new Namespace(name, new RegBlock((Block) o));
		if (o instanceof Item)
			return new Namespace(name, new RegItem((Item) o));
		return null;
	}
}
