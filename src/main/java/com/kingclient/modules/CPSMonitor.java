package com.kingclient.modules;

import com.kingclient.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class CPSMonitor extends Module {
    private final List<Long> leftClicks = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();
    private int displayX = 5;
    private int displayY = 80;

    public CPSMonitor() {
        super("CPS Monitor", "Shows clicks per second");
    }

    @Override
    public void onTick(MinecraftClient client) {
        if (client == null) return;

        long currentTime = System.currentTimeMillis();

        // Check left click
        if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), 0) == 1) {
            leftClicks.add(currentTime);
        }

        // Check right click
        if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), 1) == 1) {
            rightClicks.add(currentTime);
        }

        // Remove old clicks (older than 1 second)
        leftClicks.removeIf(time -> currentTime - time > 1000);
        rightClicks.removeIf(time -> currentTime - time > 1000);
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, MinecraftClient client, float tickDelta) {
        if (client == null) return;

        int lCps = leftClicks.size();
        int rCps = rightClicks.size();

        Text leftText = Text.literal("LMB: " + lCPS);
        Text rightText = Text.literal("RMB: " + rCps);

        client.textRenderer.drawWithShadow(matrixStack, leftText, displayX, displayY, 0xFFAA00);
        client.textRenderer.drawWithShadow(matrixStack, rightText, displayX, displayY + 12, 0x55AAFF);
    }
}
