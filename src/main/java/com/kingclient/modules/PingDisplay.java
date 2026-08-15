package com.kingclient.modules;

import com.kingclient.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class PingDisplay extends Module {
    private int displayX = 5;
    private int displayY = 105;

    public PingDisplay() {
        super("Ping Display", "Shows server ping");
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, MinecraftClient client, float tickDelta) {
        if (client == null || client.player == null) return;

        PlayerListEntry entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        if (entry == null) return;

        int ping = entry.getLatency();
        String pingColor = ping < 50 ? "§a" : ping < 100 ? "§e" : "§c";
        String pingText = "Ping: " + pingColor + ping + "ms";

        Text text = Text.literal(pingText);
        client.textRenderer.drawWithShadow(matrixStack, text, displayX, displayY, 0xFFFFFF);
    }
}
