package com.kingclient.modules;

import com.kingclient.KingClient;
import com.kingclient.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClickGUI {
    private static KeyBinding keyBinding;
    private static boolean guiOpen = false;
    private static int selectedModule = 0;

    public static void init() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kingclient.gui",
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "key.categories.kingclient"
        ));
    }

    public static void onTick(MinecraftClient client) {
        if (keyBinding.wasPressed()) {
            guiOpen = !guiOpen;
        }
    }

    public static boolean isGuiOpen() {
        return guiOpen;
    }

    public static void onRender(MatrixStack matrixStack, MinecraftClient client, float tickDelta) {
        if (!guiOpen) return;

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        // Background panel
        int panelX = width / 2 - 100;
        int panelY = height / 2 - 150;
        int panelWidth = 200;
        int panelHeight = 300;

        // Draw panel background
        fill(matrixStack, panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0x99000000);

        // Title
        client.textRenderer.drawWithShadow(matrixStack, Text.literal("King Client"), panelX + 10, panelY + 10, 0xFFDD00);

        // List modules
        int yOffset = 35;
        for (int i = 0; i < KingClient.getInstance().getModules().size(); i++) {
            Module module = KingClient.getInstance().getModules().get(i);
            int color = module.isEnabled() ? 0x00FF00 : 0x888888;
            String prefix = module.isEnabled() ? "[ON]" : "[OFF]";
            client.textRenderer.drawWithShadow(matrixStack, Text.literal(prefix + " " + module.getName()), panelX + 10, panelY + yOffset, color);
            yOffset += 15;
        }

        // Instructions
        client.textRenderer.drawWithShadow(matrixStack, Text.literal("Click module to toggle"), panelX + 10, panelY + panelHeight - 20, 0xAAAAAA);
    }

    private static void fill(MatrixStack matrixStack, int x1, int y1, int x2, int y2, int color) {
        // Simple fill using rectangles
        // In real implementation would use RenderSystem
    }
}
