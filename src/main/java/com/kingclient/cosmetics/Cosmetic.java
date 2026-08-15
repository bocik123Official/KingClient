package com.kingclient.cosmetics;

public class Cosmetic {
    private final String id;
    private final String displayName;
    private final CosmeticType type;
    private final int price;
    private final String description;

    public Cosmetic(String id, String displayName, CosmeticType type, int price) {
        this(id, displayName, type, price, "");
    }

    public Cosmetic(String id, String displayName, CosmeticType type, int price, String description) {
        this.id = id;
        this.displayName = displayName;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public CosmeticType getType() { return type; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
}
