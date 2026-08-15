package com.kingclient.modules;

import com.kingclient.KingClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ModuleGUI extends Screen {
    private int selectedCategory = 0;

    public ModuleGUI() {
        super(Text.literal("King Client - Modules"));
    }

    @Override
    protected void init() {
        int width = this.width;
        int height = this.height;

        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("X"), button -> {
            this.close();
        }).dimensions(width - 40, 10, 30, 20).build());

        // Cosmetics button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Cosmetics"), button -> {
            this.close();
            assert this.client != null;
            this.client.setScreen(new com.kingclient.cosmetics.CosmeticsScreen());
        }).dimensions(width - 120, 10, 70, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        int width = this.width;
        int height = this.height;

        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("King Client Modules"), width / 2, 10, 0xFFDD00);

        // Module list panel
        int panelX = width / 2 - 150;
        int panelY = 40;
        int panelWidth = 300;
        int panelHeight = height - 80;
        int moduleY = panelY + 10;

        KingClient kingClient = KingClient.getInstance();

        for (Module module : kingClient.getModules()) {
            int color = module.isEnabled() ? 0x00FF00 : 0x888888;
            String status = module.isEnabled() ? "[ON]" : "[OFF]";
            String displayText = status + " " + module.getName();
            
            // Draw module button background
            context.fill(panelX, moduleY, panelX + panelWidth, moduleY + 25, module.isEnabled() ? 0x5500AA00 : 0x55000000);
            context.drawTextWithShadow(this.textRenderer, Text.literal(displayText), panelX + 10, moduleY + 8, color);
            
            moduleY += 30;
            if (moduleY > panelY + panelHeight) break;
        }

        // Info at bottom
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("Click module name to toggle on/off"), 
            width / 2, height - 20, 0x888888);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            int width = this.width;
            int panelX = width / 2 - 150;
            int panelY = 40;
            int panelWidth = 300;
            int moduleY = panelY + 10;

            KingClient kingClient = KingClient.getInstance();

            for (Module module : kingClient.getModules()) {
                if (mouseX >= panelX && mouseX <= panelX + panelWidth &&
                    mouseY >= moduleY && mouseY <= moduleY + 25) {
                    
                    module.toggle();
                    break;
                }
                moduleY += 30;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
