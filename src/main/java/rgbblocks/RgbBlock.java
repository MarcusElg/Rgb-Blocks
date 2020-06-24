package rgbblocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RgbBlock extends BlockWithEntity {
	
	public RgbBlock(Settings block$Settings_1) {
		super(block$Settings_1);
	}

	public BlockRenderType getRenderType(BlockState blockState_1) {
		return BlockRenderType.MODEL;
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

	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1) {
		ItemStack itemStack = new ItemStack(this);
		RgbBlockEntity blockEntity = (RgbBlockEntity) blockView_1.getBlockEntity(blockPos_1);
		CompoundTag tag = new CompoundTag();
		tag.putInt("hue", blockEntity.hue);
		tag.putInt("saturation", blockEntity.saturation);
		tag.putInt("brightness", blockEntity.brightness);
		itemStack.setTag(tag);
		
		return itemStack;
	}

}
