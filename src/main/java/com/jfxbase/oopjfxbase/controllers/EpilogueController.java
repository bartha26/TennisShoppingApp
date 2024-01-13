package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.ShoppingCartManager;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class EpilogueController extends SceneController {
    @FXML
    private Button exit;
    @FXML
    private Button mainMenu;
    @FXML
    private AnchorPane anchorPane;
    private Image backgroundImage = new Image("C:\\Users\\TudorB\\Desktop\\tenis.jpg");

    // Create a background image
    BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)

    );
    @FXML
    public void initialize() {
        // Set the background for the VBox
        anchorPane.setBackground(new Background(background));
    }
    @FXML
    private void onExitButtonClick(){
        this.changeScene(SCENE_IDENTIFIER.GOOD_BYE);
    }
    @FXML
    private void onBackToMainMenuButtonClick(){
        ShoppingCartManager.setInstance(null);
        this.changeScene(SCENE_IDENTIFIER.HELLO);
    }
}
