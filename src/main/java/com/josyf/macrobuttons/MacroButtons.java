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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MacroButtons implements ModInitializer {

    public static final String MOD_ID = "mgbuttons";
    public static ArrayList<JSONObject> masterCommList;

    @Override
    public void onInitialize() {
        assignGuiToKey();
        initArray();
    }



    private void assignGuiToKey() {

        System.out.println("I'm getting here");

        // Currently assigns to the G key
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.macrobuttons.opengui", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "gui.macrobuttons.mgbuttons" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                System.out.println("Key 1 was pressed!");
                MinecraftClient.getInstance().openScreen(new ButtonGUIScreen(new ButtonGUI()));
                //client.player.closeScreen();
            }
        });
    }

    // player can run a command here
    public static void printMessage(String savedCommand) {
        MinecraftClient.getInstance().player.sendChatMessage(savedCommand);
    }

    public static void sayMessage(String message) {
        MinecraftClient.getInstance().player.sendChatMessage(message);
    }

    private void initArray() {
        masterCommList = ConfigFile.initArray();
        if (masterCommList == null) {
            System.out.println("Error! Master Command List is null");
        }
    }

    public static ArrayList<JSONObject> getMasterCommList() {
        return masterCommList;
    }

    public static void setMasterCommList(ArrayList<JSONObject> commList) {
        masterCommList = commList;
    }

}

