package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GoodByeView extends SceneController {
    @FXML
    private Button back;
    @FXML
    private Button exit;
    @FXML
    private VBox vBox;
    private Image backgroundImage = new Image("C:\\Users\\TudorB\\Desktop\\signIn.jpg");

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
        vBox.setBackground(new Background(background));
    }
    @FXML
    protected void onBackToHelloClick(){
        this.changeScene(SCENE_IDENTIFIER.LOGIN);
    }

    @FXML
    protected void onCloseAppClick(){
        this.closeApplication();
    }
}
