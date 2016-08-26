package net.infstudio.infinitylib.entity;

import net.infstudio.infinitylib.api.utils.TypeUtils;
import net.infstudio.infinitylib.api.seril.ITagSerializable;
import net.infstudio.infinitylib.api.vars.Var;
import net.infstudio.infinitylib.api.vars.VarSyncBase;
import net.minecraft.entity.DataWatcher;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.infstudio.infinitylib.api.seril.NBTBasement;

/**
 * @author ci010
 */
public abstract class VarWatchingImpl<T> extends VarSyncBase<T> implements Var<T>, ITagSerializable
{
	private DataWatcher delegate;
	private String name;
	private int id;

	protected VarWatchingImpl(int id, DataWatcher watcher, String name, T data)
	{
		if (data == null)
			throw new NullPointerException("The initial data cannot be null!");
		if (name == null)
			throw new NullPointerException("The name cannot be null!");
		this.name = name;
		this.delegate = watcher;
		this.id = id;
	}

	public int getId()
	{
		return this.id;
	}

	protected DataWatcher getDelegate()
	{
		return this.delegate;
	}

	public abstract T get();

	public void set(T data)
	{
		this.delegate.updateObject(id, data);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		this.set(TypeUtils.<T>cast(NBTBasement.instance().deserialize(tag.getTag(this.name))));
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTBase base = NBTBasement.instance().serialize(this.get());
		if (base != null)
			tag.setTag(this.name, base);
	}

	@Override
	public String toString()
	{
		return this.get().toString();
	}
}
