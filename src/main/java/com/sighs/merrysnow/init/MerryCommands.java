package com.sighs.merrysnow.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerPlayer;

public class MerryCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register(MerryCommands::register);
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        LiteralArgumentBuilder<CommandSourceStack> BiomeCommand =
                Commands.literal("biomeid")
                        .requires(source -> source.hasPermission(0));

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
//        BiomeCommand.executes(context -> {
//            ServerPlayer player = context.getSource().getPlayer();
//            if (player != null) {
//                String biome = Utils.getBiomeId(player);
//                if (biome != null) {
//                    sendBasicCopyMessage(player, Component.translatable("message.merrysnow.copy").getString() + " " + Component.translatable("biome." + biome.replace(":", ".")).getString(), biome);
//                }
//            }
//            return 1;
//        });

        dispatcher.register(BiomeCommand);
    }

    public static void sendBasicCopyMessage(ServerPlayer player, String displayText, String copyText) {
        MutableComponent message = Component.literal(displayText)
                .withStyle(Style.EMPTY
                        .withColor(ChatFormatting.AQUA)
                        .withClickEvent(new ClickEvent.CopyToClipboard(copyText))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Component.translatable("message.merrysnow.copy")
                        ))
                );

        player.sendSystemMessage(message);
    }
}