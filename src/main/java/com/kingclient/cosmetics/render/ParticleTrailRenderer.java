package com.kingclient.cosmetics.render;

import com.kingclient.cosmetics.Cosmetic;
import com.kingclient.cosmetics.CosmeticsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.particle.ParticleTexSheets;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ParticleTrailRenderer {
    private static final List<ParticleData> activeParticles = new ArrayList<>();
    private static int particleTick = 0;

    public static void renderTrail(AbstractClientPlayerEntity player, float tickDelta) {
        CosmeticsManager manager = CosmeticsManager.getInstance();
        Cosmetic particle = manager.getEquippedParticle();
        
        if (particle == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.particleManager == null) return;

        particleTick++;

        // Spawn particles every few ticks
        if (particleTick % 3 == 0) {
            spawnParticle(player, client, particle);
        }

        // Update and render particles
        updateParticles(player, tickDelta);
    }

    private static void spawnParticle(AbstractClientPlayerEntity player, MinecraftClient client, Cosmetic particle) {
        ClientWorld world = client.world;
        ParticleManager pm = client.particleManager;

        Vec3d pos = player.getPos();
        double x = pos.x;
        double y = pos.y + 1.0;
        double z = pos.z;

        String particleId = particle.getId();

        switch (particleId) {
            case "flame_particles" -> {
                pm.addParticle(new ColoredParticle(world, x, y, z, 0xFF4500, 0.02)); // Orange red
            }
            case "heart_particles" -> {
                pm.addParticle(new ColoredParticle(world, x, y, z, 0xFF69B4, 0.015)); // Pink
            }
            case "star_particles" -> {
                pm.addParticle(new ColoredParticle(world, x, y, z, 0xFFD700, 0.025)); // Gold
            }
            case "music_particles" -> {
                pm.addParticle(new ColoredParticle(world, x, y, z, 0x9B59B6, 0.02)); // Purple
            }
            case "crystal_particles" -> {
                pm.addParticle(new ColoredParticle(world, x, y, z, 0x00FFFF, 0.03)); // Cyan
            }
            case "void_particles" -> {
                pm.addParticle(new ColoredParticle(world, x, y, z, 0x1A1A2E, 0.02)); // Dark
            }
            case "enchanted_particles" -> {
                pm.addParticle(new ColoredParticle(world, x, y, z, 0x55FF55, 0.02)); // Enchant green
            }
            case "rainbow_particles" -> {
                float hue = (particleTick * 0.01f) % 1.0f;
                int color = java.awt.Color.HSBtoRGB(hue, 0.9f, 0.9f);
                pm.addParticle(new ColoredParticle(world, x, y, z, color, 0.025));
            }
        }
    }

    private static void updateParticles(AbstractClientPlayerEntity player, float tickDelta) {
        // Particles handle their own movement and despawning
    }

    // Simple colored particle implementation
    public static class ColoredParticle {
        private final ClientWorld world;
        private final double x, y, z;
        private final int color;
        private final double speed;
        private int age = 0;
        private final int maxAge = 40;

        public ColoredParticle(ClientWorld world, double x, double y, double z, int color, double speed) {
            this.world = world;
            this.x = x + (Math.random() - 0.5) * 0.3;
            this.y = y + (Math.random() - 0.5) * 0.2;
            this.z = z + (Math.random() - 0.5) * 0.3;
            this.color = color;
            this.speed = speed;
        }

        public void tick() {
            age++;
            // Float upward and outward
            // This would be hooked into Minecraft's particle system
        }

        public boolean isDead() {
            return age >= maxAge;
        }
    }

    private static class ParticleData {
        double x, y, z;
        int color;
        int age;
        int maxAge;

        ParticleData(double x, double y, double z, int color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.color = color;
            this.age = 0;
            this.maxAge = 40;
        }
    }
}
