package com.kingclient.cosmetics;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CosmeticsScreen extends Screen {
    private final CosmeticsManager manager;
    private CosmeticType selectedType = CosmeticType.CAPE;
    private int scrollOffset = 0;
    private Cosmetic selectedCosmetic = null;

    public CosmeticsScreen() {
        super(Text.literal("King Client Cosmetics"));
        this.manager = CosmeticsManager.getInstance();
    }

    @Override
    protected void init() {
        int width = this.width;
        int height = this.height;

        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("X"), button -> {
            this.close();
        }).dimensions(width - 40, 10, 30, 20).build());

        // Type buttons
        int buttonY = 50;
        for (CosmeticType type : CosmeticType.values()) {
            this.addDrawableChild(ButtonWidget.builder(Text.literal(type.getDisplayName()), button -> {
                selectedType = type;
                scrollOffset = 0;
            }).dimensions(10, buttonY, 100, 20).build());
            buttonY += 25;
        }

        // Daily reward button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Daily: " + manager.getDailyRewardAmount() + " pts"), button -> {
            if (manager.canClaimDaily()) {
                manager.claimDaily();
                button.setMessage(Text.literal("Claimed! +" + manager.getDailyRewardAmount() + " pts"));
            } else {
                button.setMessage(Text.literal("Already claimed!"));
            }
        }).dimensions(10, height - 40, 120, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        int width = this.width;
        int height = this.height;

        // Points display
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Points: " + manager.getPlayerPoints()), width / 2, 10, 0xFFDD00);

        // Panel background
        int panelX = 130;
        int panelY = 40;
        int panelWidth = width - 150;
        int panelHeight = height - 100;

        // Draw cosmetics list
        int cosmeticY = panelY + 10;
        int cosmeticIndex = 0;

        for (Cosmetic cosmetic : manager.getAllCosmetics()) {
            if (cosmetic.getType() != selectedType) continue;
            if (cosmeticIndex < scrollOffset) {
                cosmeticIndex++;
                continue;
            }

            boolean owned = manager.getOwnedCosmetics().contains(cosmetic);
            boolean equipped = isEquipped(cosmetic);
            boolean selected = selectedCosmetic == cosmetic;

            int textColor = owned ? (equipped ? 0x00FF00 : 0xFFFFFF) : 0xAAAAAA;
            String prefix = owned ? (equipped ? "[EQUIPPED] " : "[OWNED] ") : "[BUY] ";
            
            context.drawTextWithShadow(this.textRenderer, Text.literal(prefix + cosmetic.getDisplayName()), panelX + 10, cosmeticY, textColor);
            context.drawTextWithShadow(this.textRenderer, Text.literal(cosmetic.getPrice() + " pts"), panelX + panelWidth - 60, cosmeticY, 0xFFAA00);

            cosmeticY += 20;
            if (cosmeticY > panelY + panelHeight) break;
            cosmeticIndex++;
        }

        // Instructions
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("Click cosmetic to " + (selectedCosmetic != null && manager.getOwnedCosmetics().contains(selectedCosmetic) ? "equip/unequip" : "buy")), 
            width / 2, height - 20, 0x888888);

        super.render(context, mouseX, mouseY, delta);
    }

    private boolean isEquipped(Cosmetic cosmetic) {
        if (cosmetic.getType() == CosmeticType.CAPE) {
            return manager.getEquippedCape() == cosmetic;
        } else if (cosmetic.getType() == CosmeticType.PARTICLE_TRAIL) {
            return manager.getEquippedParticle() == cosmetic;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            // Check if clicked on a cosmetic
            int panelX = 130;
            int panelY = 40;
            int panelHeight = height - 100;
            int cosmeticY = panelY + 10;
            int cosmeticIndex = 0;

            for (Cosmetic cosmetic : manager.getAllCosmetics()) {
                if (cosmetic.getType() != selectedType) continue;
                if (cosmeticIndex < scrollOffset) {
                    cosmeticIndex++;
                    continue;
                }

                if (mouseX >= panelX + 10 && mouseX <= panelX + 300 &&
                    mouseY >= cosmeticY && mouseY <= cosmeticY + 20) {
                    
                    selectedCosmetic = cosmetic;
                    
                    if (manager.getOwnedCosmetics().contains(cosmetic)) {
                        // Toggle equip
                        if (cosmetic.getType() == CosmeticType.CAPE) {
                            if (manager.getEquippedCape() == cosmetic) {
                                manager.unequipCape();
                            } else {
                                manager.equipCape(cosmetic);
                            }
                        } else if (cosmetic.getType() == CosmeticType.PARTICLE_TRAIL) {
                            if (manager.getEquippedParticle() == cosmetic) {
                                manager.unequipParticle();
                            } else {
                                manager.equipParticle(cosmetic);
                            }
                        }
                    } else {
                        // Try to purchase
                        if (manager.purchaseCosmetic(cosmetic)) {
                            // Purchased successfully
                        }
                    }
                    break;
                }

                cosmeticY += 20;
                if (cosmeticY > panelY + panelHeight) break;
                cosmeticIndex++;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount > 0) {
            scrollOffset = Math.max(0, scrollOffset - 1);
        } else if (verticalAmount < 0) {
            scrollOffset++;
        }
        return true;
    }
}
