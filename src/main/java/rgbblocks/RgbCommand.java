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

public class RgbCommand {

	public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
		commandDispatcher.register(ServerCommandManager.literal("setcolor")
				.then(ServerCommandManager.argument("hue", IntegerArgumentType.integer(0, 255)).executes(context -> {
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
						}).then(ServerCommandManager.argument("brightness", IntegerArgumentType.integer(0, 255))
								.executes(RgbCommand::setColor)))));

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

}
