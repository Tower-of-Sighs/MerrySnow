package com.sighs.merrysnow.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.sighs.merrysnow.event.SpawnEvents;
import com.sighs.merrysnow.init.EventHooks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
    @Inject(method = "isValidPositionForMob", at = @At("RETURN"), cancellable = true)
    private static void merrysnow$isValidPositionForMob(ServerLevel serverLevel, Mob mob, double d, CallbackInfoReturnable<Boolean>
            cir) {
        double threshold = mob.getType().getCategory().getDespawnDistance() * mob.getType().getCategory().getDespawnDistance();
        if (d > threshold && mob.removeWhenFarAway(d)) {
            cir.setReturnValue(false);
            return;
        }
        cir.setReturnValue(EventHooks.checkSpawnPosition(mob, serverLevel, MobSpawnType.NATURAL));
    }

    @WrapOperation(
            method = "spawnMobsForChunkGeneration",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z"
            )
    )
    private static boolean merrysnow$wrapChunkGenRules(Mob mob, LevelAccessor level, MobSpawnType spawnType, Operation<Boolean> original) {
        return true;
    }

    @WrapOperation(
            method = "spawnMobsForChunkGeneration",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;checkSpawnObstruction(Lnet/minecraft/world/level/LevelReader;)Z"
            )
    )
    private static boolean merrysnow$wrapChunkGenObstruction(Mob mob, LevelReader level, Operation<Boolean> original) {
        var serverAccessor = (ServerLevelAccessor) level;
        var result = SpawnEvents.POSITION_CHECK.invoker().onCheck(mob, serverAccessor, MobSpawnType.CHUNK_GENERATION, null);
        if (result == SpawnEvents.Result.SUCCEED) return true;
        if (result == SpawnEvents.Result.FAIL) return false;
        boolean rules = mob.checkSpawnRules(serverAccessor, MobSpawnType.CHUNK_GENERATION);
        boolean obstruction = original.call(mob, level);
        return rules && obstruction;
    }
}