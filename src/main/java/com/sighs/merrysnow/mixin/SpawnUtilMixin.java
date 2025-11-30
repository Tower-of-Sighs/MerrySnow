package com.sighs.merrysnow.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.sighs.merrysnow.event.SpawnEvents;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpawnUtil.class)
public class SpawnUtilMixin {

    @WrapOperation(
            method = "trySpawnMob",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/EntitySpawnReason;)Z"
            )
    )
    private static boolean merrysnow$wrapTrySpawnRules(Mob mob, LevelAccessor level, EntitySpawnReason entitySpawnReason, Operation<Boolean> original) {
        return true;
    }

    @WrapOperation(
            method = "trySpawnMob",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;checkSpawnObstruction(Lnet/minecraft/world/level/LevelReader;)Z"
            )
    )
    private static boolean merrysnow$wrapTrySpawnObstruction(Mob mob, LevelReader level, Operation<Boolean> original) {
        var serverAccessor = (ServerLevelAccessor) level;
        var result = SpawnEvents.POSITION_CHECK.invoker().onCheck(mob, serverAccessor, EntitySpawnReason.NATURAL, null);
        if (result == SpawnEvents.Result.SUCCEED) return true;
        if (result == SpawnEvents.Result.FAIL) return false;
        boolean rules = mob.checkSpawnRules(serverAccessor, EntitySpawnReason.NATURAL);
        boolean obstruction = original.call(mob, level);
        return rules && obstruction;
    }
}
