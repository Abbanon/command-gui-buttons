package com.josyf.macrobuttons.gui;

import com.josyf.macrobuttons.ConfigFile;
import com.josyf.macrobuttons.MacroButtons;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.TranslatableText;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ButtonGUI extends LightweightGuiDescription {

    int xValue = 0;
    int yValue = 1;

    private static String ConfigSettings = ConfigFile.readFile();

    public ButtonGUI() {

        // initialize root panel of GUI
        WGridPanel root = new WGridPanel();

        setupBackground(root);

        // ######### DEBUG BUTTONS ############
        // example button to create config JSON
        WButton button = new WButton(new TranslatableText("Serialize"));
        button.setOnClick(() -> {
            ConfigFile.serializeCommand();
        });
        root.add(button, xValue, yValue + 9, 4, 1);

        // example load serialization button
        WButton button2 = new WButton(new TranslatableText("Load Serialization"));
        button2.setOnClick(() -> {
            ConfigFile.loadSerialization();
        });
        root.add(button2, xValue + 4, yValue + 9, 6, 1);

        // read json file button
        WButton button3 = new WButton(new TranslatableText("Read command json"));
        button3.setOnClick(() -> {
            ConfigFile.readFile();
        });
        root.add(button3, xValue + 10, yValue + 9, 6, 1);


        // read mastercommlist
        // read json file button
        WButton button4 = new WButton(new TranslatableText("Read master list"));
        button4.setOnClick(() -> {
            System.out.println(MacroButtons.getMasterCommList());
        });
        root.add(button4, xValue + 10, yValue + 8, 6, 1);

        // ######### DEBUG BUTTONS ############

        // Text GUI, not needed yet
        // WLabel label = new WLabel(new LiteralText("Test"), 0xFFFFFF);
        // root.add(label, 0, 4, 2, 1);

        addSavedButtons(root);

        addCommandSection(root);

        root.validate(this);
    }

    public static String getConfig() {
        return ConfigSettings;
    }

    private void addCommandSection(WGridPanel root) {
        // Add text field for command NAME entry
        WTextField nameTextField = new WTextField();
        nameTextField.setSuggestion("Name");
        root.add(nameTextField, 0, 12, 6, 1);

        // Add text field for command / entry
        WTextField commandTextField = new WTextField();
        commandTextField.setSuggestion("/command");
        commandTextField.setMaxLength(100);
        root.add(commandTextField, 6, 12, 11, 1);

        // Add button for command entry
        WButton addCmdBtn = new WButton(new TranslatableText("+"));
        addCmdBtn.setOnClick(() -> {
            addGUIButton(root, xValue, nameTextField, commandTextField);
        });
        root.add(addCmdBtn, 18, 12, 1, 1);
    }

    // function to save newly added buttons to commands.json
    private void addGUIButton(WGridPanel root, int x, WTextField name, WTextField command) {
        // Only add the button if there are contents in both
        if (!name.getText().equals("") && !command.getText().equals("")) {

            //System.out.println("Here, command is " + command.getText());
            String instancedString = command.getText();

            WButton button = new WButton(new TranslatableText(name.getText()));
            button.setOnClick(() -> {
                System.out.println("Command was " + instancedString);
                MacroButtons.runCommand(instancedString);
            });

            root.add(button, xValue, yValue, 4, 1);

            // Create a new Json object & append to masterCommList
            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("name", name.getText());
            newJsonObject.put("command", command.getText());

            ArrayList<JSONObject> commListCopy = MacroButtons.getMasterCommList();

            if (commListCopy != null) {
                commListCopy.add(newJsonObject);

                // Add jsonObject to commands.json
                ConfigFile.appendToFile(newJsonObject);

                MacroButtons.setMasterCommList(commListCopy);
            } else {
                ConfigFile.appendToFile(newJsonObject);
            }


            adjustBounds();

            name.setText("");
            command.setText("");

            root.validate(this);

        } else {
            System.out.println("No name and value entered!");
        }

    }

    // function to load buttons from commands.json
    private void addGUIButton(WGridPanel root, int x, String name, String command) {
        if (!name.equals("") && !command.equals("")) {

            //System.out.println("Here, command is " + command);
            String instancedString = command;

            WButton button = new WButton(new TranslatableText(name));
            button.setOnClick(() -> {
                System.out.println("Command was " + instancedString);
                MacroButtons.runCommand(instancedString);
            });
            // int newX = incrementNumber(x, 4);
            //System.out.println("x val: " + xValue);
            //System.out.println("y val: " + yValue);


            root.add(button, xValue, yValue, 4, 1);



            adjustBounds();

            root.validate(this);

        } else {
            System.out.println("No name and value entered!");
        }
    }


    // Array will contain String class types. Convert these to objects.
    private void addSavedButtons(WGridPanel root) {
        //JSONArray stringCommList = MacroButtons.masterCommList;
        ArrayList<JSONObject> commListCopy = MacroButtons.getMasterCommList();
        // Array will contain String class types. Convert these to objects
        System.out.println("I be doin my thing here");
        // Then convert the objects to buttons
        if (commListCopy != null) {
            for (int i = 0; i < commListCopy.size(); i++) {
                String name = commListCopy.get(i).get("name").toString();
                String command = commListCopy.get(i).get("command").toString();
                addGUIButton(root, xValue, name, command);
            }
        }

    }


    private void adjustBounds() {
        if (xValue % 12 == 0 && xValue != 0) {
            yValue += 2;
            xValue = 0;
        } else {
            xValue += 4;
        }
    }

    // Change background panel color to transparent black
    @Override
    public void addPainters() {
        super.addPainters();
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x4D000000));
    }

    private void setupBackground(WGridPanel root) {
        setRootPanel(root);
        root.setSize(350, 240);
    }


}
