package net.infstudio.infinitylib.api.seril;

import net.infstudio.infinitylib.api.utils.Nullable;
import net.infstudio.infinitylib.api.utils.NotNull;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author ci010
 */
public interface ITagSerializer<T>
{
	@NotNull
	T readFromNBT(NBTTagCompound tag, @Nullable T data);

	void writeToNBT(NBTTagCompound tag, T data);
}
