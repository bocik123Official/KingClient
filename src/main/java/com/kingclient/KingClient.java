package com.kingclient;

import com.kingclient.cosmetics.CosmeticsManager;
import com.kingclient.events.PauseEventHandler;
import com.kingclient.gui.KingPauseScreen;
import com.kingclient.modules.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class KingClient implements ClientModInitializer {
    public static final String MOD_ID = "kingclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String VERSION = "1.0.0";

    private static KingClient instance;
    private final List<Module> modules = new ArrayList<>();
    private CosmeticsManager cosmeticsManager;
    private KeyBinding guiKey;

    // Module instances
    private HUD hud;
    private Keystrokes keystrokes;
    private CPSMonitor cpsMonitor;
    private PingDisplay pingDisplay;

    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("King Client v" + VERSION + " initializing...");

        // Initialize cosmetics
        cosmeticsManager = new CosmeticsManager();

        // Initialize pause menu handler (ESC key)
        PauseEventHandler.init();

        // Register keybind for GUI (Right Shift)
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kingclient.gui",
            net.minecraft.client.input.KeybirdMapping.RIGHT_SHIFT,
            "key.categories.kingclient"
        ));

        // Initialize modules
        hud = new HUD();
        keystrokes = new Keystrokes();
        cpsMonitor = new CPSMonitor();
        pingDisplay = new PingDisplay();

        modules.add(hud);
        modules.add(keystrokes);
        modules.add(cpsMonitor);
        modules.add(pingDisplay);

        // Register tick events
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            onTick(client);
        });

        // Register HUD render callback
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            onHudRender(matrixStack, tickDelta);
        });

        LOGGER.info("King Client initialized successfully!");
        LOGGER.info("Premium client loaded with " + modules.size() + " modules");
        LOGGER.info("Cosmetics system: " + cosmeticsManager.getAllCosmetics().size() + " items available");
        LOGGER.info("ESC -> King Pause Menu");
    }

    private void onTick(MinecraftClient client) {
        // Check GUI key (Right Shift)
        if (guiKey.wasPressed()) {
            if (client.currentScreen == null) {
                client.setScreen(new ModuleGUI());
            }
        }

        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onTick(client);
            }
        }
    }

    private void onHudRender(net.minecraft.client.util.math.MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) return;

        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onHudRender(matrixStack, client, tickDelta);
            }
        }
    }

    public static KingClient getInstance() {
        return instance;
    }

    public List<Module> getModules() {
        return modules;
    }

    public CosmeticsManager getCosmeticsManager() {
        return cosmeticsManager;
    }

    public HUD getHud() { return hud; }
    public Keystrokes getKeystrokes() { return keystrokes; }
    public CPSMonitor getCpsMonitor() { return cpsMonitor; }
    public PingDisplay getPingDisplay() { return pingDisplay; }
}
