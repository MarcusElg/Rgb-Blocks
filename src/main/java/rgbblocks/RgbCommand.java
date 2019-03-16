package rgbblocks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TextFormat;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.RayTraceContext.FluidHandling;
import net.minecraft.world.RayTraceContext.ShapeType;

public class RgbCommand {

	public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
		commandDispatcher.register(
				ServerCommandManager.literal("setcolor").then(ServerCommandManager.literal("set").executes(context -> {
					ServerPlayerEntity player = context.getSource().getPlayer();
					TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.toofewarguments");
					text.setStyle(new Style().setColor(TextFormat.RED));
					player.addChatMessage(text, false);
					return 0;
				}).then(ServerCommandManager.argument("hue", IntegerArgumentType.integer(0, 255)).executes(context -> {
					ServerPlayerEntity player = context.getSource().getPlayer();
					TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.toofewarguments");
					text.setStyle(new Style().setColor(TextFormat.RED));
					player.addChatMessage(text, false);
					return 0;
				}).then(ServerCommandManager.argument("saturation", IntegerArgumentType.integer(0, 255))
						.executes(context -> {
							ServerPlayerEntity player = context.getSource().getPlayer();
							TranslatableTextComponent text = new TranslatableTextComponent(
									"command.rgb.toofewarguments");
							text.setStyle(new Style().setColor(TextFormat.RED));
							player.addChatMessage(text, false);
							return 0;
						})
						.then(ServerCommandManager.argument("brightness", IntegerArgumentType.integer(0, 255))
								.executes(RgbCommand::setColor)))))
						.then(ServerCommandManager.literal("add").executes(context -> {
							ServerPlayerEntity player = context.getSource().getPlayer();
							TranslatableTextComponent text = new TranslatableTextComponent(
									"command.rgb.toofewarguments");
							text.setStyle(new Style().setColor(TextFormat.RED));
							player.addChatMessage(text, false);
							return 0;
						}).then(ServerCommandManager.argument("hue", IntegerArgumentType.integer(-255, 255))
								.executes(context -> {
									ServerPlayerEntity player = context.getSource().getPlayer();
									TranslatableTextComponent text = new TranslatableTextComponent(
											"command.rgb.toofewarguments");
									text.setStyle(new Style().setColor(TextFormat.RED));
									player.addChatMessage(text, false);
									return 0;
								}).then(ServerCommandManager.argument("saturation", IntegerArgumentType.integer(-255, 255))
										.executes(context -> {
											ServerPlayerEntity player = context.getSource().getPlayer();
											TranslatableTextComponent text = new TranslatableTextComponent(
													"command.rgb.toofewarguments");
											text.setStyle(new Style().setColor(TextFormat.RED));
											player.addChatMessage(text, false);
											return 0;
										})
										.then(ServerCommandManager
												.argument("brightness", IntegerArgumentType.integer(-255, 255))
												.executes(RgbCommand::addColor)))))
						.then(ServerCommandManager.literal("get").executes(RgbCommand::getColor)));
	}

	public static int setColor(CommandContext<ServerCommandSource> commandSource) throws CommandSyntaxException {
		ItemStack itemStack = commandSource.getSource().getPlayer().getStackInHand(Hand.MAIN);
		int hue = IntegerArgumentType.getInteger(commandSource, "hue");
		int saturation = IntegerArgumentType.getInteger(commandSource, "saturation");
		int brightness = IntegerArgumentType.getInteger(commandSource, "brightness");

		if (itemStack != null && itemStack.getItem().getItemGroup() == RgbBlocks.itemGroup) {
			CompoundTag tag = itemStack.getTag();
			if (tag == null) {
				tag = new CompoundTag();
			}

			tag.putInt("hue", hue);
			tag.putInt("saturation", saturation);
			tag.putInt("brightness", brightness);

			itemStack.setTag(tag);

			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.sucess");
			player.addChatMessage(text, false);

			return 1;
		} else {
			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.wrongitem");
			text.setStyle(new Style().setColor(TextFormat.RED));
			player.addChatMessage(text, false);
			return 0;
		}
	}

	public static int addColor(CommandContext<ServerCommandSource> commandSource) throws CommandSyntaxException {
		ItemStack itemStack = commandSource.getSource().getPlayer().getStackInHand(Hand.MAIN);
		int hue = IntegerArgumentType.getInteger(commandSource, "hue");
		int saturation = IntegerArgumentType.getInteger(commandSource, "saturation");
		int brightness = IntegerArgumentType.getInteger(commandSource, "brightness");

		if (itemStack != null && itemStack.getItem().getItemGroup() == RgbBlocks.itemGroup) {
			CompoundTag tag = itemStack.getTag();
			if (tag == null) {
				tag = new CompoundTag();
			}

			int currentHue = tag.getInt("hue") + hue;
			
			if (currentHue < 0) {
				currentHue += 255;
			} else if (currentHue > 255) {
				currentHue -= 255;
			}
			
			int currentSaturation = tag.getInt("saturation");
			int currentBrightness = tag.getInt("brightness");
			
			tag.putInt("hue", currentHue);
			tag.putInt("saturation", Math.min(Math.max(currentSaturation + saturation, 0), 255));
			tag.putInt("brightness", Math.min(Math.max(currentBrightness + brightness, 0), 255));

			itemStack.setTag(tag);

			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.sucess");
			player.addChatMessage(text, false);

			return 1;
		} else {
			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.wrongitem");
			text.setStyle(new Style().setColor(TextFormat.RED));
			player.addChatMessage(text, false);
			return 0;
		}
	}

	public static int getColor(CommandContext<ServerCommandSource> commandSource) throws CommandSyntaxException {
		ItemStack itemStack = commandSource.getSource().getPlayer().getStackInHand(Hand.MAIN);
		ServerPlayerEntity player = commandSource.getSource().getPlayer();

		if (itemStack != null && itemStack.getItem().getItemGroup() == RgbBlocks.itemGroup) {
			Vec3d endPoint = player.getCameraPosVec(1).add(player.getRotationVec(1).x * 5, player.getRotationVec(1).y * 5, player.getRotationVec(1).z * 5);
			BlockHitResult hitResult = player.getServerWorld().rayTrace(new RayTraceContext(player.getCameraPosVec(1), endPoint, ShapeType.OUTLINE, FluidHandling.NONE, player));
			BlockPos hitBlockPos = hitResult.getBlockPos();

			if (hitResult.getSide() == Direction.UP) {
				hitBlockPos.add(0, -1, 0);
			} else if (hitResult.getSide() == Direction.SOUTH) {
				hitBlockPos.add(-1, 0, 0);
			} else if (hitResult.getSide() == Direction.EAST) {
				hitBlockPos.add(0, 0, -1);
			}
			
			if (hitResult.getType() == Type.BLOCK && player.getEntityWorld().getBlockState(hitBlockPos).getBlock().getItem().getItemGroup() == RgbBlocks.itemGroup) {
				RgbBlockEntity blockEntity = (RgbBlockEntity) player.getEntityWorld().getBlockEntity(hitBlockPos);
	        	
	        	CompoundTag tag = itemStack.getTag();
				if (tag == null) {
					tag = new CompoundTag();
				}

				tag.putInt("hue", blockEntity.hue);
				tag.putInt("saturation", blockEntity.saturation);
				tag.putInt("brightness", blockEntity.brightness);

				itemStack.setTag(tag);

				TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.sucess");
				player.addChatMessage(text, false);

				return 1;
	        } else {
	        	TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.wrongblock");
				text.setStyle(new Style().setColor(TextFormat.RED));
				player.addChatMessage(text, false);
	        	return 0;
	        }
		} else {
			TranslatableTextComponent text = new TranslatableTextComponent("command.rgb.wrongitem");
			text.setStyle(new Style().setColor(TextFormat.RED));
			player.addChatMessage(text, false);
			return 0;
		}
	}

}
