package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.Users;
import com.jfxbase.oopjfxbase.utils.SQLDatabase;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import com.jfxbase.oopjfxbase.utils.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class SignInController extends SceneController {
    @FXML
    public TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Text userPrompt;
    @FXML
    private Button back;
    @FXML
    private Button signIn;
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
    AnchorPane anchorPane = new AnchorPane();
    @FXML
    public void initialize() {
        // Set the background for the VBox
        anchorPane.setBackground(new Background(background));
    }
    @FXML
    protected void onSignInButton()
    {
        Users user = new Users(userName.getText(),password.getText());
        if (SQLDatabase.registerUser(user.getUser_name(), user.getPassword())) {
            this.userName.clear();
            this.password.clear();
            this.userPrompt.setText("Please introduce a username and a password");
            this.changeScene(SCENE_IDENTIFIER.LOGIN);
            System.out.println("User registered successfully");
        } else {
            userPrompt.setText("You introduced wrong data!");
            System.out.println("Error registering user. Username may already exist.");
        }
    }
    @FXML
    protected void onBackButton()
    {
        this.userName.clear();
        this.password.clear();
        this.userPrompt.setText("Please introduce a username and a password");
        this.changeScene(SCENE_IDENTIFIER.LOGIN);
    }
}
