package com.kingclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Module {
    protected String name;
    protected String description;
    protected boolean enabled;
    protected int keybind;

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
        this.enabled = false;
        this.keybind = 0;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isEnabled() { return enabled; }
    public int getKeybind() { return keybind; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setKeybind(int keybind) { this.keybind = keybind; }
    public void toggle() { this.enabled = !this.enabled; }

    public void onTick(MinecraftClient client) {}
    public void onHudRender(MatrixStack matrixStack, MinecraftClient client, float tickDelta) {}
}
