package rgbblocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class RgbCarpet extends BlockWithEntity {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	public RgbCarpet(Settings block$Settings_1) {
		super(block$Settings_1);
	}

	public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
		return SHAPE;
	}

	public BlockRenderType getRenderType(BlockState blockState_1) {
		return BlockRenderType.MODEL;
	}
	
	public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
		return !blockState.canPlaceAt(worldAccess, blockPos) ? Blocks.AIR.getDefaultState()
				: super.getStateForNeighborUpdate(blockState, direction, blockState2, worldAccess, blockPos, blockPos2);
	}

	public boolean canPlaceAt(WorldView worldView, BlockPos blockPos) {
		return !worldView.isAir(blockPos.down(1));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView blockView) {
		return new RgbBlockEntity();
	}

	public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1,
			ItemStack itemStack_1) {
		if (itemStack_1.hasTag() == true) {
			RgbBlockEntity blockEntity = (RgbBlockEntity) world_1.getBlockEntity(blockPos_1);
			blockEntity.hue = itemStack_1.getTag().getInt("hue");
			blockEntity.saturation = itemStack_1.getTag().getInt("saturation");
			blockEntity.brightness = itemStack_1.getTag().getInt("brightness");
		}
	}
}
