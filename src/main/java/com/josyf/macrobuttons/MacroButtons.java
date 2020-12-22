package com.josyf.macrobuttons;



import com.josyf.macrobuttons.gui.ButtonGUI;
import com.josyf.macrobuttons.gui.ButtonGUIScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.json.simple.JSONObject;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class MacroButtons implements ModInitializer {

    public static final String MOD_ID = "mgbuttons";
    private static ArrayList<JSONObject> masterCommList;

    public static void main(String[] args) {

    }

    @Override
    public void onInitialize() {
        assignGuiToKey();
        initArray();
    }


    private void assignGuiToKey() {
        // Currently assigns to the G key
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.macrobuttons.opengui", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "gui.macrobuttons.mgbuttons" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                MinecraftClient.getInstance().openScreen(new ButtonGUIScreen(new ButtonGUI()));
                //client.player.closeScreen();
            }
        });
    }

    public static void runCommand(String command) {
        MinecraftClient.getInstance().player.sendChatMessage(command);
    }

    // Assign masterCommList to JSONArray<objects> (from commands.json). Runs once.
    static void initArray() {
        masterCommList = ConfigFile.getArrayFromJsonFile();
        // If commands.json doesn't exist yet, start a global list variable for future creation
        if (masterCommList == null) {
            setMasterCommList(new ArrayList<>());
        }
    }

    public static ArrayList<JSONObject> getMasterCommList() {
        return masterCommList;
    }

    public static void setMasterCommList(ArrayList<JSONObject> commList) {
        masterCommList = commList;
    }

}

