package rgbblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

public class RgbCarpet extends BlockWithEntity {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	public RgbCarpet(Settings block$Settings_1) {
		super(block$Settings_1);
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1,
			EntityContext entityContext) {
		return SHAPE;
	}

	public BlockRenderType getRenderType(BlockState blockState_1) {
		return BlockRenderType.MODEL;
	}
	
	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2,
			IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		return !blockState_1.canPlaceAt(iWorld_1, blockPos_1) ? Blocks.AIR.getDefaultState()
				: super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1,
						blockPos_2);
	}

	public boolean canPlaceAt(BlockState blockState_1, ViewableWorld viewableWorld_1, BlockPos blockPos_1) {
		return !viewableWorld_1.isAir(blockPos_1.down());
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
