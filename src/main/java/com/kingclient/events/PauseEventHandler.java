package com.kingclient.events;

import com.kingclient.gui.KingPauseScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class PauseEventHandler {
    private static KeyBinding escKey;
    
    public static void init() {
        escKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kingclient.pause",
            GLFW.GLFW_KEY_ESCAPE,
            "key.categories.kingclient"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (escKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new KingPauseScreen());
                }
            }
        });
    }
}
