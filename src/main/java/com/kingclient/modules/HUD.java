package com.kingclient.modules;

import com.kingclient.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class HUD extends Module {
    private int displayX = 5;
    private int displayY = 5;

    public HUD() {
        super("HUD", "Displays FPS, Server IP and Coordinates");
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, MinecraftClient client, float tickDelta) {
        if (client == null || client.options == null) return;

        int currentY = displayY;

        // FPS
        int fps = client.getCurrentFps();
        Text fpsText = Text.literal("FPS: " + fps);
        client.textRenderer.drawWithShadow(matrixStack, fpsText, displayX, currentY, 0x00FF00);
        currentY += 12;

        // Server IP
        if (client.getCurrentServerEntry() != null) {
            ServerInfo server = client.getCurrentServerEntry();
            Text serverText = Text.literal("Server: " + server.address);
            client.textRenderer.drawWithShadow(matrixStack, serverText, displayX, currentY, 0xFFFFFF);
            currentY += 12;
        }

        // Coordinates
        if (client.player != null) {
            String coords = String.format("XYZ: %.1f / %.1f / %.1f",
                client.player.getX(), client.player.getY(), client.player.getZ());
            Text coordsText = Text.literal(coords);
            client.textRenderer.drawWithShadow(matrixStack, coordsText, displayX, currentY, 0x55FFFF);
        }
    }
}
