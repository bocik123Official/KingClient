package com.kingclient.cosmetics.render;

import com.kingclient.cosmetics.Cosmetic;
import com.kingclient.cosmetics.CosmeticType;
import com.kingclient.cosmetics.CosmeticsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CapeRenderer {

    public static void renderCape(MatrixStack matrices, AbstractClientPlayerEntity player, PlayerEntityModel<AbstractClientPlayerEntity> model, float tickDelta) {
        CosmeticsManager manager = CosmeticsManager.getInstance();
        Cosmetic cape = manager.getEquippedCape();
        
        if (cape == null) return;

        matrices.push();
        
        // Position cape on player back
        model.cape.render(matrices, 0xFFFFFF, 0);
        
        // Apply cosmetic-specific rendering
        String capeId = cape.getId();
        
        switch (capeId) {
            case "fire_cape" -> renderFireCape(matrices, model);
            case "ice_cape" -> renderIceCape(matrices, model);
            case "shadow_cape" -> renderShadowCape(matrices, model);
            case "rainbow_cape" -> renderRainbowCape(matrices, model, tickDelta);
            case "galaxy_cape" -> renderGalaxyCape(matrices, model, tickDelta);
            case "king_cape" -> renderKingCape(matrices, model);
            default -> model.cape.render(matrices, 0xFFFFFF, 0);
        }
        
        matrices.pop();
    }

    private static void renderKingCape(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model) {
        // Golden royal cape with dark red inside
        matrices.push();
        model.cape.render(matrices, 0x8B0000, 0); // Dark red inside
        matrices.pop();
    }

    private static void renderFireCape(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model) {
        // Orange-red gradient fire cape
        matrices.push();
        model.cape.render(matrices, 0xFF4500, 0); // Orange red
        matrices.pop();
    }

    private static void renderIceCape(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model) {
        // Light blue icy cape
        matrices.push();
        model.cape.render(matrices, 0xADD8E6, 0); // Light blue
        matrices.pop();
    }

    private static void renderShadowCape(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model) {
        // Dark purple/black shadowy cape
        matrices.push();
        model.cape.render(matrices, 0x2E0854, 0); // Deep purple
        matrices.pop();
    }

    private static void renderRainbowCape(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model, float tickDelta) {
        // Cycling rainbow colors
        float hue = (tickDelta * 0.1f) % 1.0f;
        int color = java.awt.Color.HSBtoRGB(hue, 0.8f, 0.9f);
        
        matrices.push();
        model.cape.render(matrices, color, 0);
        matrices.pop();
    }

    private static void renderGalaxyCape(MatrixStack matrices, PlayerEntityModel<AbstractClientPlayerEntity> model, float tickDelta) {
        // Deep blue/purple galaxy colors
        matrices.push();
        model.cape.render(matrices, 0x1A0533, 0); // Very dark purple
        matrices.pop();
    }
}
