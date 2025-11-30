package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SnowLayerBlock.class)
public class SnowLayerBlockMixin {
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void merry(BlockState p_154285_, BlockGetter p_154286_, BlockPos p_154287_, CollisionContext p_154288_, CallbackInfoReturnable<VoxelShape> cir) {
        boolean enabled;
        try {
            enabled = Config.MERRY_SNOW_LAYER.get();
        } catch (IllegalStateException e) {
            enabled = true;
        }
        if (!enabled) return;
        cir.setReturnValue(Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
    }
}