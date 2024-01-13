package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.UserManager;
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

import java.sql.SQLException;

public class LoginViewController extends SceneController{
    @FXML
    private Button SignIn;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Text userPrompt;
    private SQLDatabase sqlDatabase = new SQLDatabase();
    private Image backgroundImage = new Image("C:\\Users\\TudorB\\Desktop\\Federer-forehand.jpg");

    // Create a background image
    BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)

    );
    @FXML
    private VBox vBox = new VBox();
    @FXML
    public void initialize() {
        // Set the background for the VBox
        vBox.setBackground(new Background(background));
    }
    @FXML
    protected void onGoToGoodBye() {
        this.changeScene(SCENE_IDENTIFIER.GOOD_BYE);
    }
    @FXML
    protected void onLoginButtonClick() {
        Users user = new Users(userName.getText(),password.getText());
        SQLDatabase sqlDatabase = new SQLDatabase();
       // String enteredPassword = password.getText();
       // String enteredUserName = userName.getText();
        if (sqlDatabase.authenticateUser(user.getUser_name(),user.getPassword())) {
            if(user.getUser_name().equals("TudorAdmin"))
            {
                this.userName.clear();
                this.password.clear();
                this.changeScene(SCENE_IDENTIFIER.SUPER_USER);
            }
            else {
                System.out.println("Authentication successful");
                userPrompt.setText("WELCOME!");
                UserManager.getInstance().setCurrentUser(user);
                UserManager.getInstance().getCurrentUser().setId(sqlDatabase.fetchDataUserId(user.getUser_name()));
                this.userName.clear();
                this.password.clear();
                this.changeScene(SCENE_IDENTIFIER.HELLO);
            }
        } else {
            this.userName.clear();
            this.password.clear();
            userPrompt.setText(String.format("User name or password not correct: %s %s ", userName.getText(), password.getText()));
            System.out.println("Authentication failed");
        }
    }
    @FXML
    protected void onSignInButtonClick(){
        this.changeScene(SCENE_IDENTIFIER.SIGN_IN);
    }
}
