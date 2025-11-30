package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.init.EventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnPlacements.class)
public class SpawnPlacementsMixin {
    @Inject(method = "checkSpawnRules", at = @At("RETURN"), cancellable = true)
    private static void merrysnow$checkSpawnRules(EntityType<?> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean>
            cir) {
        boolean vanilla = cir.getReturnValue();
        cir.setReturnValue(EventHooks.checkSpawnPlacements(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource, vanilla));
    }
}