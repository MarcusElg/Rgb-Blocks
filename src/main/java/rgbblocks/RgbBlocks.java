package rgbblocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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

	public static Block rgbTerracottaSlab;
	public static Block rgbWoolSlab;
	public static Block rgbConcreteSlab;
	public static Block rgbGlassSlab;
	public static Block rgbPlanksSlab;

	public static Block rgbTerracottaStairs;
	public static Block rgbWoolStairs;
	public static Block rgbConcreteStairs;
	public static Block rgbGlassStairs;
	public static Block rgbPlanksStairs;

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

		rgbTerracottaSlab = registerSlab(Blocks.WHITE_TERRACOTTA, "terracotta");
		rgbWoolSlab = registerSlab(Blocks.WHITE_WOOL, "wool");
		rgbPlanksSlab = registerSlab(Blocks.BIRCH_PLANKS, "planks");
		rgbConcreteSlab = registerSlab(Blocks.WHITE_CONCRETE, "concrete");
		rgbGlassSlab = registerSlab(Blocks.WHITE_STAINED_GLASS, "glass", BlockRenderLayer.TRANSLUCENT);

		rgbTerracottaStairs = registerStairs(rgbTerracotta.getDefaultState(), Blocks.WHITE_TERRACOTTA, "terracotta");
		rgbWoolStairs = registerStairs(rgbWool.getDefaultState(), Blocks.WHITE_WOOL, "wool");
		rgbPlanksStairs = registerStairs(rgbPlanks.getDefaultState(), Blocks.BIRCH_PLANKS, "planks");
		rgbConcreteStairs = registerStairs(rgbConcrete.getDefaultState(), Blocks.WHITE_CONCRETE, "concrete");
		rgbGlassStairs = registerStairs(rgbGlass.getDefaultState(), Blocks.WHITE_STAINED_GLASS, "glass",
				BlockRenderLayer.TRANSLUCENT);

		rgbCarpet = registerCarpet(Blocks.WHITE_CARPET, "carpet");

		FlammableBlockRegistry.getDefaultInstance().add(rgbPlanks, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(rgbWool, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(rgbCarpet, 60, 20);

		FlammableBlockRegistry.getDefaultInstance().add(rgbPlanksSlab, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(rgbWoolSlab, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(rgbPlanksStairs, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(rgbWoolStairs, 30, 60);

		CommandRegistry.INSTANCE.register(false, RgbCommand::register);
		blockEntity = BlockEntityType.Builder.create(RgbBlockEntity::new, getRgbBlocks()).build(null);

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

	private Block registerSlab(Block block, String name) {
		return registerSlab(block, name, BlockRenderLayer.CUTOUT);
	}

	private Block registerSlab(Block block, String name, BlockRenderLayer blockRenderLayer) {
		Block block2 = new RgbSlab(Block.Settings.copy(block), blockRenderLayer);
		Registry.register(Registry.BLOCK, new Identifier("rgbblocks", name + "_slab"), block2);
		BlockItem blockItem = new BlockItem(block2, new Item.Settings().itemGroup(itemGroup));
		Registry.register(Registry.ITEM, new Identifier("rgbblocks", name + "_slab"), blockItem);
		return block2;
	}

	private Block registerStairs(BlockState baseBlockState, Block block, String name) {
		return registerStairs(baseBlockState, block, name, BlockRenderLayer.CUTOUT);
	}

	private Block registerStairs(BlockState baseBlockState, Block block, String name,
			BlockRenderLayer blockRenderLayer) {
		Block block2 = new RgbStairs(baseBlockState, Block.Settings.copy(block), blockRenderLayer);
		Registry.register(Registry.BLOCK, new Identifier("rgbblocks", name + "_stairs"), block2);
		BlockItem blockItem = new BlockItem(block2, new Item.Settings().itemGroup(itemGroup));
		Registry.register(Registry.ITEM, new Identifier("rgbblocks", name + "_stairs"), blockItem);
		return block2;
	}

	private Block registerCarpet(Block block, String name) {
		Block block2 = new RgbCarpet(Block.Settings.copy(block));
		Registry.register(Registry.BLOCK, new Identifier("rgbblocks", name), block2);
		BlockItem blockItem = new BlockItem(block2, new Item.Settings().itemGroup(itemGroup));
		Registry.register(Registry.ITEM, new Identifier("rgbblocks", name), blockItem);
		return block2;
	}

	public Block[] getRgbBlocks() {
		return new Block[]{rgbTerracotta,rgbWool,rgbPlanks,rgbConcrete,rgbConcretePowder,rgbGlass,rgbTerracottaSlab,rgbWoolSlab,rgbPlanksSlab,rgbConcreteSlab,rgbGlassSlab,rgbTerracottaStairs,rgbWoolStairs,rgbPlanksStairs,rgbConcreteStairs,rgbGlassStairs,rgbCarpet};
	}

}
