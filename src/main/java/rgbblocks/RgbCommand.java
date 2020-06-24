package rgbblocks;

import java.awt.Color;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
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
		commandDispatcher
				.register(CommandManager.literal("setcolor").then(CommandManager.literal("set").executes(context -> {
					ServerPlayerEntity player = context.getSource().getPlayer();
					TranslatableText text = new TranslatableText("command.rgb.nocolourtype");
					text.setStyle(Style.EMPTY.withColor(Formatting.RED));
					player.sendMessage(text, false);
					return 0;
				}).then(CommandManager.literal("hsv").executes(context -> {
					ServerPlayerEntity player = context.getSource().getPlayer();
					TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
					text.setStyle(Style.EMPTY.withColor(Formatting.RED));
					player.sendMessage(text, false);
					return 0;
				}).then(CommandManager.argument("hue", IntegerArgumentType.integer(0, 255)).executes(context -> {
					ServerPlayerEntity player = context.getSource().getPlayer();
					TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
					text.setStyle(Style.EMPTY.withColor(Formatting.RED));
					player.sendMessage(text, false);
					return 0;
				}).then(CommandManager.argument("saturation", IntegerArgumentType.integer(0, 255)).executes(context -> {
					ServerPlayerEntity player = context.getSource().getPlayer();
					TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
					text.setStyle(Style.EMPTY.withColor(Formatting.RED));
					player.sendMessage(text, false);
					return 0;
				}).then(CommandManager.argument("brightness", IntegerArgumentType.integer(0, 255))
						.executes(RgbCommand::setColorHsv))))).then(CommandManager.literal("rgb").executes(context -> {
							ServerPlayerEntity player = context.getSource().getPlayer();
							TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
							text.setStyle(Style.EMPTY.withColor(Formatting.RED));
							player.sendMessage(text, false);
							return 0;
						}).then(CommandManager.argument("red", IntegerArgumentType.integer(0, 255))
								.executes(context -> {
									ServerPlayerEntity player = context.getSource().getPlayer();
									TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
									text.setStyle(Style.EMPTY.withColor(Formatting.RED));
									player.sendMessage(text, false);
									return 0;
								}).then(CommandManager.argument("green", IntegerArgumentType.integer(0, 255))
										.executes(context -> {
											ServerPlayerEntity player = context.getSource().getPlayer();
											TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
											text.setStyle(Style.EMPTY.withColor(Formatting.RED));
											player.sendMessage(text, false);
											return 0;
										})
										.then(CommandManager.argument("blue", IntegerArgumentType.integer(0, 255))
												.executes(RgbCommand::setColorRgb))))))
						.then(CommandManager.literal("add").executes(context -> {
							ServerPlayerEntity player = context.getSource().getPlayer();
							TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
							text.setStyle(Style.EMPTY.withColor(Formatting.RED));
							player.sendMessage(text, false);
							return 0;
						}).then(CommandManager.argument("hue", IntegerArgumentType.integer(-255, 255))
								.executes(context -> {
									ServerPlayerEntity player = context.getSource().getPlayer();
									TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
									text.setStyle(Style.EMPTY.withColor(Formatting.RED));
									player.sendMessage(text, false);
									return 0;
								}).then(CommandManager.argument("saturation", IntegerArgumentType.integer(-255, 255))
										.executes(context -> {
											ServerPlayerEntity player = context.getSource().getPlayer();
											TranslatableText text = new TranslatableText("command.rgb.toofewarguments");
											text.setStyle(Style.EMPTY.withColor(Formatting.RED));
											player.sendMessage(text, false);
											return 0;
										})
										.then(CommandManager
												.argument("brightness", IntegerArgumentType.integer(-255, 255))
												.executes(RgbCommand::addColorHsv)))))
						.then(CommandManager.literal("get").executes(RgbCommand::getColor)));
	}

	public static int setColorRgb(CommandContext<ServerCommandSource> commandSource) throws CommandSyntaxException {
		ItemStack itemStack = commandSource.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
		int red = IntegerArgumentType.getInteger(commandSource, "red");
		int green = IntegerArgumentType.getInteger(commandSource, "green");
		int blue = IntegerArgumentType.getInteger(commandSource, "blue");

		if (itemStack != null && itemStack.getItem().getGroup() == RgbBlocks.itemGroup) {
			CompoundTag tag = itemStack.getTag();
			if (tag == null) {
				tag = new CompoundTag();
			}

			float[] hsv = Color.RGBtoHSB(red, green, blue, null);

			tag.putInt("hue", Math.round(hsv[0] * 255f));
			tag.putInt("saturation", Math.round(hsv[1] * 255f));
			tag.putInt("brightness", Math.round(hsv[2] * 255f));

			itemStack.setTag(tag);

			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableText text = new TranslatableText("command.rgb.sucess");
			player.sendMessage(text, false);

			return 1;
		} else {
			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableText text = new TranslatableText("command.rgb.wrongitem");
			text.setStyle(Style.EMPTY.withColor(Formatting.RED));
			player.sendMessage(text, false);
			return 0;
		}
	}

	public static int setColorHsv(CommandContext<ServerCommandSource> commandSource) throws CommandSyntaxException {
		ItemStack itemStack = commandSource.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
		int hue = IntegerArgumentType.getInteger(commandSource, "hue");
		int saturation = IntegerArgumentType.getInteger(commandSource, "saturation");
		int brightness = IntegerArgumentType.getInteger(commandSource, "brightness");

		if (itemStack != null && itemStack.getItem().getGroup() == RgbBlocks.itemGroup) {
			CompoundTag tag = itemStack.getTag();
			if (tag == null) {
				tag = new CompoundTag();
			}

			tag.putInt("hue", hue);
			tag.putInt("saturation", saturation);
			tag.putInt("brightness", brightness);

			itemStack.setTag(tag);

			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableText text = new TranslatableText("command.rgb.sucess");
			player.sendMessage(text, false);

			return 1;
		} else {
			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableText text = new TranslatableText("command.rgb.wrongitem");
			text.setStyle(Style.EMPTY.withColor(Formatting.RED));
			player.sendMessage(text, false);
			return 0;
		}
	}

	public static int addColorHsv(CommandContext<ServerCommandSource> commandSource) throws CommandSyntaxException {
		ItemStack itemStack = commandSource.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
		int hue = IntegerArgumentType.getInteger(commandSource, "hue");
		int saturation = IntegerArgumentType.getInteger(commandSource, "saturation");
		int brightness = IntegerArgumentType.getInteger(commandSource, "brightness");

		if (itemStack != null && itemStack.getItem().getGroup() == RgbBlocks.itemGroup) {
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
			TranslatableText text = new TranslatableText("command.rgb.sucess");
			player.sendMessage(text, false);

			return 1;
		} else {
			ServerPlayerEntity player = commandSource.getSource().getPlayer();
			TranslatableText text = new TranslatableText("command.rgb.wrongitem");
			text.setStyle(Style.EMPTY.withColor(Formatting.RED));
			player.sendMessage(text, false);
			return 0;
		}
	}

	public static int getColor(CommandContext<ServerCommandSource> commandSource) throws CommandSyntaxException {
		ItemStack itemStack = commandSource.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
		ServerPlayerEntity player = commandSource.getSource().getPlayer();

		if (itemStack != null && itemStack.getItem().getGroup() == RgbBlocks.itemGroup) {
			Vec3d endPoint = player.getCameraPosVec(1).add(player.getRotationVec(1).x * 5,
					player.getRotationVec(1).y * 5, player.getRotationVec(1).z * 5);
			BlockHitResult hitResult = player.getServerWorld().rayTrace(new RayTraceContext(player.getCameraPosVec(1),
					endPoint, ShapeType.OUTLINE, FluidHandling.NONE, player));
			BlockPos hitBlockPos = hitResult.getBlockPos();

			if (hitResult.getSide() == Direction.UP) {
				hitBlockPos.add(0, -1, 0);
			} else if (hitResult.getSide() == Direction.SOUTH) {
				hitBlockPos.add(-1, 0, 0);
			} else if (hitResult.getSide() == Direction.EAST) {
				hitBlockPos.add(0, 0, -1);
			}

			if (hitResult.getType() == Type.BLOCK
					&& player.world.getBlockState(hitBlockPos).getBlock().asItem().getGroup() == RgbBlocks.itemGroup) {
				RgbBlockEntity blockEntity = (RgbBlockEntity) player.world.getBlockEntity(hitBlockPos);

				CompoundTag tag = itemStack.getTag();
				if (tag == null) {
					tag = new CompoundTag();
				}

				tag.putInt("hue", blockEntity.hue);
				tag.putInt("saturation", blockEntity.saturation);
				tag.putInt("brightness", blockEntity.brightness);

				itemStack.setTag(tag);

				TranslatableText text = new TranslatableText("command.rgb.sucess");
				player.sendMessage(text, false);

				return 1;
			} else {
				TranslatableText text = new TranslatableText("command.rgb.wrongblock");
				text.setStyle(Style.EMPTY.withColor(Formatting.RED));
				player.sendMessage(text, false);
				return 0;
			}
		} else {
			TranslatableText text = new TranslatableText("command.rgb.wrongitem");
			text.setStyle(Style.EMPTY.withColor(Formatting.RED));
			player.sendMessage(text, false);
			return 0;
		}
	}

}
