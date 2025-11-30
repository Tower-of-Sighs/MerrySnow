package com.sighs.merrysnow.init;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ClientUtils {
    public static float randomSunnySnow;

    public static boolean isKeyPressed(int glfwKeyCode) {
        if (glfwKeyCode == -1) return false;

        var minecraft = Minecraft.getInstance();
        long windowHandle = minecraft.getWindow().getWindow();
        if (windowHandle == 0L) return false;

        // GLFW 是否初始化且有效
        try {
            return GLFW.glfwGetKey(windowHandle, glfwKeyCode) == GLFW.GLFW_PRESS;
        } catch (Exception e) {
            return false;
        }
    }
}