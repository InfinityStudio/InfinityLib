package net.infstudio.infinitylib.api.seril;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface ITagSerializable
{
	void readFromNBT(NBTTagCompound tag);

	void writeToNBT(NBTTagCompound tag);
}
