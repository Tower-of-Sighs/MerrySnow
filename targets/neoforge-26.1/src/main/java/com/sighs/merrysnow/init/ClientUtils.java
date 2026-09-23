package com.sighs.merrysnow.init;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ClientUtils {
    public static boolean isKeyPressed(int glfwKeyCode) {
        if (glfwKeyCode == -1) return false;

        Minecraft minecraft = Minecraft.getInstance();
        // 26.1 renamed Window#getWindow() to Window#handle().
        long windowHandle = minecraft.getWindow().handle();
        if (windowHandle == 0L) return false; // 窗口未准备好

        // GLFW 是否初始化且有效
        try {
            return GLFW.glfwGetKey(windowHandle, glfwKeyCode) == GLFW.GLFW_PRESS;
        } catch (Exception e) {
            return false;
        }
    }
}
