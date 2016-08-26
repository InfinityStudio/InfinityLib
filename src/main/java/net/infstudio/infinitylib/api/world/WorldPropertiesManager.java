package net.infstudio.infinitylib.api.world;

import net.infstudio.infinitylib.api.registry.ModProxy;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * @author ci010
 */
public interface WorldPropertiesManager
{
	@ModProxy.Inject(genericType = WorldPropertiesManager.class)
	WorldPropertiesManager INSTANCE = null;

	ICapabilityProvider getCapabilityProvider(int dimension);

	ICapabilityProvider getCapabilityProvider(World world);

	ChunkData getChunkData(World world, BlockPos pos);

	ChunkData getChunkData(World world, ChunkCoordIntPair pos);

	ChunkData getChunkData(Chunk chunk);
}
