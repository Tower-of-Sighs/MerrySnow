package com.sighs.merrysnow.mixin;

import com.sighs.merrysnow.event.ConfigAction;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fabric API exposes no mouse scroll event, so the sunny snow scroll wheel binding is driven from
 * the vanilla handler. {@code ConfigAction} only consumes the sign of the delta, so the raw GLFW
 * offset is forwarded unchanged.
 */
@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void merrysnow$wheelAction(long window, double xOffset, double yOffset, CallbackInfo ci) {
        if (ConfigAction.wheelAction(yOffset)) {
            ci.cancel();
        }
    }
}
