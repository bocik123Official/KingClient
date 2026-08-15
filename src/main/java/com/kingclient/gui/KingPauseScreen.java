package com.kingclient.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.recipe.v1.FabricClientRecipeGroups;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class KingPauseScreen extends GameMenuScreen {

    public KingPauseScreen() {
        super(false);
    }

    @Override
    protected void init() {
        super.init();
        int width = this.width;
        int height = this.height;

        // Add King Module button to the pause menu
        this.addDrawableChild(ButtonWidget.builder(Text.literal("King Module"), button -> {
            this.client.setScreen(new com.kingclient.modules.ModuleGUI());
        }).dimensions(width / 2 - 100, height / 2 + 60, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        
        // Draw King Client branding at the top
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("King Client"), this.width / 2, this.height / 2 - 100, 0xFFDD00);
    }
}
