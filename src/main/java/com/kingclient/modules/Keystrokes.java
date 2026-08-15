package com.kingclient.modules;

import com.kingclient.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class Keystrokes extends Module {
    private int offsetX = 0;
    private int offsetY = 60;

    public Keystrokes() {
        super("Keystrokes", "Shows pressed keys on screen");
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, MinecraftClient client, float tickDelta) {
        if (client == null || client.options == null) return;

        int x = client.getWindow().getScaledWidth() / 2 + offsetX;
        int y = client.getWindow().getScaledHeight() / 2 + offsetY;

        KeyBinding[] keyBindings = {
            client.options.forwardKey,
            client.options.backKey,
            client.options.leftKey,
            client.options.rightKey,
            client.options.jumpKey,
            client.options.sprintKey
        };

        String[] keyNames = {"W", "S", "A", "D", "SPACE", "CTRL"};

        for (int i = 0; i < keyBindings.length; i++) {
            boolean isPressed = keyBindings[i].isPressed();
            int color = isPressed ? 0x00FF00 : 0x888888;
            Text keyText = Text.literal(keyNames[i]);
            client.textRenderer.drawWithShadow(matrixStack, keyText, x + (i % 2) * 25, y + (i / 2) * 12, color);
        }
    }
}
