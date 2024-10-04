package com.example.fullbright;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FullbrightMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("fullbrightmod");

    // Store the original gamma value to restore it later
    private double originalGamma = 1.0;
    private boolean fullbrightEnabled = false;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Fullbright Mod");

        // Register a client tick event to check for keypresses and toggle fullbright
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client != null && client.player != null) {
                // Toggle fullbright with a key press (for example F5)
                if (client.options.keyTogglePerspective.wasPressed()) {
                    toggleFullbright(client);
                }
            }
        });
    }

    // Method to toggle fullbright on or off
    private void toggleFullbright(MinecraftClient client) {
        if (fullbrightEnabled) {
            client.options.getGamma().setValue(originalGamma); // Restore original gamma
            fullbrightEnabled = false;
            LOGGER.info("Fullbright disabled");
        } else {
            originalGamma = client.options.getGamma().getValue(); // Save the current gamma
            client.options.getGamma().setValue(100.0); // Set gamma to a high value for fullbright
            fullbrightEnabled = true;
            LOGGER.info("Fullbright enabled");
        }
    }
}
