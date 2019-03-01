package rgbblocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RgbBlocks implements ModInitializer {

	public static ItemGroup itemGroup;

	public static Block rgbTerracotta;
	public static Block rgbWool;
	public static Block rgbCarpet;
	public static Block rgbConcrete;
	public static Block rgbConcretePowder;
	public static Block rgbGlass;
	public static Block rgbPlanks;

	public static BlockEntityType<RgbBlockEntity> blockEntity;

	@Override
	public void onInitialize() {
		itemGroup = FabricItemGroupBuilder.create(new Identifier("rgbblocks", "rgbblocks"))
				.icon(() -> new ItemStack(rgbTerracotta)).build();

		rgbTerracotta = registerBlock(Blocks.WHITE_TERRACOTTA, "terracotta");
		rgbWool = registerBlock(Blocks.WHITE_WOOL, "wool");
		rgbPlanks = registerBlock(Blocks.BIRCH_PLANKS, "planks");
		rgbConcrete = registerBlock(Blocks.WHITE_CONCRETE, "concrete");
		rgbConcretePowder = registerBlock(Blocks.WHITE_CONCRETE_POWDER, "concrete_powder");
		rgbGlass = registerBlock(Blocks.WHITE_STAINED_GLASS, "glass", BlockRenderLayer.TRANSLUCENT);

		rgbCarpet = registerCarpet(Blocks.WHITE_CARPET, "carpet");

		FlammableBlockRegistry.getDefaultInstance().add(rgbPlanks, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(rgbWool, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(rgbCarpet, 60, 20);

		CommandRegistry.INSTANCE.register(false, RgbCommand::register);
		blockEntity = BlockEntityType.Builder.create(RgbBlockEntity::new).build(null);
		
		Registry.register(Registry.BLOCK_ENTITY, new Identifier("rgbblock", "rgbblock"), blockEntity);
	}

	private Block registerBlock(Block block, String name) {
		return registerBlock(block, name, BlockRenderLayer.SOLID);
	}

	private Block registerBlock(Block block, String name, BlockRenderLayer blockRenderLayer) {
		Block block2 = new RgbBlock(Block.Settings.copy(block), blockRenderLayer);
		Registry.register(Registry.BLOCK, new Identifier("rgbblocks", name), block2);
		BlockItem blockItem = new BlockItem(block2, new Item.Settings().itemGroup(itemGroup));
		Registry.register(Registry.ITEM, new Identifier("rgbblocks", name), blockItem);
		return block2;
	}

	private Block registerCarpet(Block block, String name) {
		Block block2 = new RgbCarpet(Block.Settings.copy(block));
		Registry.register(Registry.BLOCK, new Identifier("rgbblocks", name), block2);
		BlockItem blockItem = new BlockItem(block2, new Item.Settings().itemGroup(itemGroup));
		Registry.register(Registry.ITEM, new Identifier("rgbblocks", name), blockItem);
		return block2;
	}

}
