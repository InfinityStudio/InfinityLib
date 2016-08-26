package net.infstudio.infinitylib.api.entity;

import net.infstudio.infinitylib.api.seril.ITagSerializable;
import net.infstudio.infinitylib.api.vars.VarSyncFactory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A easy interface to create a {@link net.minecraftforge.common.IExtendedEntityProperties}.
 * <p>Use {@link EntityHandler} to attach this on an entity.</p>
 *
 * @author ci010
 * @see net.minecraftforge.common.IExtendedEntityProperties
 * @see EntityHandler
 */
public interface IStatus extends ITagSerializable
{
	/**
	 * The initialization method of this status. This will be called when entity just created.
	 * <p>
	 * The factory here can create the variables that sync automatically between client and server. So you don't
	 * need to consider about the sync problem when you use these variables.
	 * </p>
	 * <p>
	 * They are implemented by {@link net.minecraft.entity.DataWatcher} which has a maximum limit. So use this only
	 * if you want a value
	 * showing on screen or having any other usages in client side.
	 * </p>
	 * <p/>
	 * The variable created by factory also doesn't need to be saved and loaded when
	 * {@link ITagSerializable#readFromNBT(NBTTagCompound)} and {@link ITagSerializable#writeToNBT(NBTTagCompound)}, since they
	 * will be saved automatically.
	 * <p/>
	 * The name of the variable when you create it is the reference for save/load. So, changing its name will
	 * lost the old data.
	 * <p/>
	 *
	 * @param entity  The entity.
	 * @param factory The factory which provide the variable auto-synchronized between Server and Client sides.
	 */
	void build(Entity entity, VarSyncFactory factory);
}
