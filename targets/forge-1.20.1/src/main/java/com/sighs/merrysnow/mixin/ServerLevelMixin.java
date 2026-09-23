package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.Config;
import com.sighs.merrysnow.init.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ServerLevel.class)
public class ServerLevelMixin {
//    @Redirect(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
//    private boolean modifySnow(Biome biome, LevelReader levelReader, BlockPos blockPos) {
//        if (Config.ENFORCE_SNOW_COVER.get().equals("default")) {
//            String id = Utils.getBiomeId((ServerLevel) (Object) this, biome);
//            String result = Config.getSnowModify(id);
//            if (result.equals("default")) {
//                return biome.shouldSnow(levelReader, blockPos);
//            } else return !Boolean.parseBoolean(result);
//        }
//        else return Boolean.parseBoolean(Config.ENFORCE_SNOW_COVER.get());
//    }
}
