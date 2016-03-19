package test.api.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

/**
 * @author ci010
 */
public interface PowerProvider extends ComponentBlock
{
	/**
	 * isProvidingWeakPower
	 *
	 * @param worldIn
	 * @param pos
	 * @param state
	 * @param side
	 * @return
	 */
	int providePower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side);
}
