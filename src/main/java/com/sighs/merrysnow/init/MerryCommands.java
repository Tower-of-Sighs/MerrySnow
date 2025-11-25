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
        LiteralArgumentBuilder<CommandSourceStack> BiomeCommand =
                Commands.literal("getBiome")
                        .requires(source -> source.hasPermission(0));

        BiomeCommand.executes(context -> {
            ServerPlayer player = context.getSource().getPlayer();
            if (player != null) {
                String biome = Utils.getBiomeId(player);
                if (biome != null) {
                    sendBasicCopyMessage(player, Component.translatable("message.merrysnow.copy").getString() + " " + biome, biome);
                }
            }
            return 1;
        });

        dispatcher.register(BiomeCommand);
    }

    public static void sendBasicCopyMessage(ServerPlayer player, String displayText, String copyText) {
        MutableComponent message = Component.literal(displayText)
                .withStyle(Style.EMPTY
                        .withColor(ChatFormatting.AQUA)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copyText))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("message.merrysnow.copy")))
                );

        player.sendSystemMessage(message);
    }
}
