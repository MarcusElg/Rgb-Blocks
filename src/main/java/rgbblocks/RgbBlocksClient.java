package rgbblocks;

import java.awt.Color;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class RgbBlocksClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Colours
		registerBlockColour(RgbBlocks.rgbTerracotta);
		registerBlockColour(RgbBlocks.rgbWool);
		registerBlockColour(RgbBlocks.rgbPlanks);
		registerBlockColour(RgbBlocks.rgbGlass);
		registerBlockColour(RgbBlocks.rgbConcrete);
		registerBlockColour(RgbBlocks.rgbConcretePowder);
		registerBlockColour(RgbBlocks.rgbCarpet);

		registerBlockColour(RgbBlocks.rgbTerracottaSlab);
		registerBlockColour(RgbBlocks.rgbWoolSlab);
		registerBlockColour(RgbBlocks.rgbPlanksSlab);
		registerBlockColour(RgbBlocks.rgbGlassSlab);
		registerBlockColour(RgbBlocks.rgbConcreteSlab);

		registerBlockColour(RgbBlocks.rgbTerracottaStairs);
		registerBlockColour(RgbBlocks.rgbWoolStairs);
		registerBlockColour(RgbBlocks.rgbPlanksStairs);
		registerBlockColour(RgbBlocks.rgbGlassStairs);
		registerBlockColour(RgbBlocks.rgbConcreteStairs);

		// Render layers
		BlockRenderLayerMap.INSTANCE.putBlock(RgbBlocks.rgbGlass, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RgbBlocks.rgbGlassSlab, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RgbBlocks.rgbGlassStairs, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(RgbBlocks.rgbCarpet, RenderLayer.getCutout());
	}

	public void registerBlockColour(Block block) {
		ColorProviderRegistry.BLOCK.register((block3, blockView, position, layer) -> {

			if (blockView.getBlockEntity(position) != null
					&& blockView.getBlockEntity(position).getType() == RgbBlocks.blockEntity) {
				RgbBlockEntity rgbBlockEntity = (RgbBlockEntity) blockView.getBlockEntity(position);
				return Color.HSBtoRGB(rgbBlockEntity.hue / 255f, rgbBlockEntity.saturation / 255f,
						rgbBlockEntity.brightness / 255f);
			} else {
				return Color.HSBtoRGB(0, 0, 1);
			}
		}, block);

		ColorProviderRegistry.ITEM.register((item, layer) -> {
			if (item.hasTag() == true) {
				if (item.getTag().contains("hue") && item.getTag().contains("saturation")
						&& item.getTag().contains("brightness")) {
					return Color.HSBtoRGB(item.getTag().getInt("hue") / 255f, item.getTag().getInt("saturation") / 255f,
							item.getTag().getInt("brightness") / 255f);
				} else {
					return Color.HSBtoRGB(0, 0, 1);
				}
			} else {
				return Color.HSBtoRGB(0, 0, 1);
			}
		}, block.asItem());
	}

}
