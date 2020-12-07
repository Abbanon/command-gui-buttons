package com.josyf.macrobuttons;



import com.josyf.macrobuttons.gui.ButtonGUI;
import com.josyf.macrobuttons.gui.ButtonGUIScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MacroButtons implements ModInitializer {

    public static final String MOD_ID = "mgbuttons";

    int xValue;
    int yValue;



    @Override
    public void onInitialize() {
        assignGuiToKey();
    }

    private void assignGuiToKey() {

        System.out.println("I'm getting here");

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.macrobuttons.opengui", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "gui.macrobuttons.mgbuttons" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                // client.player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
                MinecraftClient.getInstance().openScreen(new ButtonGUIScreen(new ButtonGUI()));
                //printMessage();
            }
        });
    }


    // player can run a command here
    public static void printMessage() {
        MinecraftClient.getInstance().player.sendChatMessage("/seed");
    }

    // player adds command button to GUI
    public static void addGUIButton() {
        MinecraftClient.getInstance().player.sendChatMessage("Adding button!");
    }

    public int incrementNumber(int a, int b) {
        return a+b;
    }

}

