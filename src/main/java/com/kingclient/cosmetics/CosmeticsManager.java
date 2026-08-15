package com.kingclient.cosmetics;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerEntityPeer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CosmeticsManager {
    private static CosmeticsManager instance;
    private final List<Cosmetic> allCosmetics = new ArrayList<>();
    private final List<Cosmetic> ownedCosmetics = new ArrayList<>();
    private Cosmetic equippedCape = null;
    private Cosmetic equippedParticle = null;
    private int playerPoints = 0;
    private long lastDailyClaim = 0;

    public CosmeticsManager() {
        instance = this;
        initializeCosmetics();
    }

    private void initializeCosmetics() {
        // CAPES
        allCosmetics.add(new Cosmetic("king_cape", "King Cape", CosmeticType.CAPE, 500));
        allCosmetics.add(new Cosmetic("fire_cape", "Fire Cape", CosmeticType.CAPE, 750));
        allCosmetics.add(new Cosmetic("ice_cape", "Ice Cape", CosmeticType.CAPE, 750));
        allCosmetics.add(new Cosmetic("shadow_cape", "Shadow Cape", CosmeticType.CAPE, 1000));
        allCosmetics.add(new Cosmetic("rainbow_cape", "Rainbow Cape", CosmeticType.CAPE, 1500));
        allCosmetics.add(new Cosmetic("galaxy_cape", "Galaxy Cape", CosmeticType.CAPE, 2000));

        // PARTICLE TRAILS
        allCosmetics.add(new Cosmetic("flame_particles", "Flame Trail", CosmeticType.PARTICLE_TRAIL, 300));
        allCosmetics.add(new Cosmetic("heart_particles", "Heart Trail", CosmeticType.PARTICLE_TRAIL, 300));
        allCosmetics.add(new Cosmetic("star_particles", "Star Trail", CosmeticType.PARTICLE_TRAIL, 400));
        allCosmetics.add(new Cosmetic("music_particles", "Music Notes", CosmeticType.PARTICLE_TRAIL, 500));
        allCosmetics.add(new Cosmetic("crystal_particles", "Crystal Trail", CosmeticType.PARTICLE_TRAIL, 600));
        allCosmetics.add(new Cosmetic("void_particles", "Void Trail", CosmeticType.PARTICLE_TRAIL, 800));
        allCosmetics.add(new Cosmetic("enchanted_particles", "Enchanted Trail", CosmeticType.PARTICLE_TRAIL, 1000));
        allCosmetics.add(new Cosmetic("rainbow_particles", "Rainbow Trail", CosmeticType.PARTICLE_TRAIL, 1200));
    }

    public boolean purchaseCosmetic(Cosmetic cosmetic) {
        if (playerPoints >= cosmetic.getPrice() && !ownedCosmetics.contains(cosmetic)) {
            playerPoints -= cosmetic.getPrice();
            ownedCosmetics.add(cosmetic);
            return true;
        }
        return false;
    }

    public boolean canClaimDaily() {
        long currentTime = System.currentTimeMillis();
        long oneDayMs = 24 * 60 * 60 * 1000;
        return currentTime - lastDailyClaim >= oneDayMs;
    }

    public void claimDaily() {
        if (canClaimDaily()) {
            playerPoints += 50; // 50 punktów za codzienne logowanie
            lastDailyClaim = System.currentTimeMillis();
        }
    }

    public int getDailyRewardAmount() {
        return 50;
    }

    public void equipCape(Cosmetic cape) {
        if (ownedCosmetics.contains(cape) && cape.getType() == CosmeticType.CAPE) {
            equippedCape = cape;
        }
    }

    public void equipParticle(Cosmetic particle) {
        if (ownedCosmetics.contains(particle) && particle.getType() == CosmeticType.PARTICLE_TRAIL) {
            equippedParticle = particle;
        }
    }

    public void unequipCape() {
        equippedCape = null;
    }

    public void unequipParticle() {
        equippedParticle = null;
    }

    public Cosmetic getEquippedCape() { return equippedCape; }
    public Cosmetic getEquippedParticle() { return equippedParticle; }
    public List<Cosmetic> getAllCosmetics() { return allCosmetics; }
    public List<Cosmetic> getOwnedCosmetics() { return ownedCosmetics; }
    public int getPlayerPoints() { return playerPoints; }
    public void addPoints(int amount) { playerPoints += amount; }
    public long getLastDailyClaim() { return lastDailyClaim; }

    public static CosmeticsManager getInstance() {
        return instance;
    }
}
