package com.kingclient.cosmetics;

public enum CosmeticType {
    CAPE("Capes"),
    PARTICLE_TRAIL("Particle Trails"),
    HAT("Hats"),
    WINGS("Wings"),
    BACKPACK("Backpacks"),
    AURA("Auras"),
    PET("Pets");

    private final String displayName;

    CosmeticType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
