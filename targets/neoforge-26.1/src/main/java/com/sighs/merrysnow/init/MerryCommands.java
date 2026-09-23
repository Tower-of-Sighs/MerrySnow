package com.sighs.merrysnow.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerPlayer;

public class MerryCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // 26.1 replaced CommandSourceStack#hasPermission(int) with the PermissionSet API.
        // The original predicate was hasPermission(0), which is true for every source,
        // so the command is simply registered without a requirement.
        LiteralArgumentBuilder<CommandSourceStack> BiomeCommand =
                Commands.literal("biomeid");

        BiomeCommand.then(Commands.literal("current").executes(context -> {
            ServerPlayer player = context.getSource().getPlayer();
            if (player != null) {
                String biome = Utils.getBiomeId(player);
                if (biome != null) {
                    sendBasicCopyMessage(player, Component.translatable("message.merrysnow.copy").getString() + " " + Component.translatable("biome." + biome.replace(":", ".")).getString(), biome);
                }
            }
            return 1;
        }));
        BiomeCommand.then(Commands.literal("all").executes(context -> {
            ServerPlayer player = context.getSource().getPlayer();
            if (player != null) {
                String biome = Utils.getAlBiomeId(player);
                sendBasicCopyMessage(player, Component.translatable("message.merrysnow.copy").getString() + " [...]", biome);
            }
            return 1;
        }));

        dispatcher.register(BiomeCommand);
    }

    public static void sendBasicCopyMessage(ServerPlayer player, String displayText, String copyText) {
        MutableComponent message = Component.literal(displayText)
                .withStyle(Style.EMPTY
                        .withColor(ChatFormatting.AQUA)
                        .withClickEvent(new ClickEvent.CopyToClipboard(copyText))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Component.translatable("message.merrysnow.copy")))
                );

        player.sendSystemMessage(message);
    }
}
