package rgbblocks;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class RgbBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

	public int hue = 0;
	public int saturation = 0;
	public int brightness = 255;

	public RgbBlockEntity() {
		super(RgbBlocks.blockEntity);
	}

	public void fromTag(CompoundTag compoundTag_1) {
		super.fromTag(compoundTag_1);
		this.hue = compoundTag_1.getInt("hue");
		this.saturation = compoundTag_1.getInt("saturation");
		this.brightness = compoundTag_1.getInt("brightness");
	}

	public CompoundTag toTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		compoundTag_1.putInt("hue", Math.min(Math.max(hue, 0), 255));
		compoundTag_1.putInt("saturation", Math.min(Math.max(saturation, 0), 255));
		compoundTag_1.putInt("brightness", Math.min(Math.max(brightness, 0), 255));
		return compoundTag_1;
	}

	@Override
	public void fromClientTag(CompoundTag compoundTag_1) {
		super.fromTag(compoundTag_1);
		this.hue = compoundTag_1.getInt("hue");
		this.saturation = compoundTag_1.getInt("saturation");
		this.brightness = compoundTag_1.getInt("brightness");
	}

	@Override
	public CompoundTag toClientTag(CompoundTag compoundTag_1) {
		super.toTag(compoundTag_1);
		compoundTag_1.putInt("hue", Math.min(Math.max(hue, 0), 255));
		compoundTag_1.putInt("saturation", Math.min(Math.max(saturation, 0), 255));
		compoundTag_1.putInt("brightness", Math.min(Math.max(brightness, 0), 255));
		return compoundTag_1;
	}

}
