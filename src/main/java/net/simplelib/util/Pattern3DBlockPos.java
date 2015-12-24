package net.simplelib.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.util.BlockPos;

import java.util.List;

/**
 * The implementation of Pattern3D into BlockPos.
 *
 * @author ci010
 */
public class Pattern3DBlockPos extends Pattern3D
{
	protected List<BlockPos> sub;

	private Pattern3DBlockPos(List<Vector> pos)
	{
		super(pos);
	}

	public static Pattern3DBlockPos newPattern(List<BlockPos> poses)
	{
		List<Vector> temp = Lists.newArrayList();
		for (BlockPos pose : poses)
			temp.add(new BlockAdaptorVector(pose));
		return new Pattern3DBlockPos(temp);
	}

	public ImmutableList<BlockPos> transferTo(BlockPos origin)
	{
		ImmutableList.Builder<BlockPos> builder = ImmutableList.builder();
		for (BlockPos pos : sub)
			builder.add(pos.add(origin.getX(), origin.getY(), origin.getZ()));
		return builder.build();
	}

	static class BlockAdaptorVector implements Vector
	{
		BlockPos pos;

		public BlockAdaptorVector(BlockPos pos)
		{
			this.pos = pos;
		}

		@Override
		public int getX()
		{
			return pos.getX();
		}

		@Override
		public int getY()
		{
			return pos.getY();
		}

		@Override
		public int getZ()
		{
			return pos.getZ();
		}

		@Override
		public Pattern3D.Vector offset(int x, int y, int z)
		{
			return new BlockAdaptorVector(pos.add(x, y, z));
		}

		@Override
		public int compareTo(Pattern3D.Vector o)
		{
			return getX() - o.getX() + getY() - o.getY() + getZ() - o.getZ();
		}
	}
}
