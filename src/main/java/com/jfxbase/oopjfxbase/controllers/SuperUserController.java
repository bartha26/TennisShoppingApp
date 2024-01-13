package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.Users;
import com.jfxbase.oopjfxbase.utils.SQLDatabase;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SuperUserController extends SceneController {
    @FXML
    private Button back;
    @FXML
    private Button showData;
    @FXML
    private Button addUser;
    @FXML
    private Button deleteUser;
    @FXML
    private Button updatePassword;
    @FXML
    private AnchorPane anchorPane = new AnchorPane();
    @FXML
    public TableView<Users> table;
    @FXML
    private Text text;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField userPassword;
    @FXML
    private PasswordField userPassword2;
    boolean x=false;
    SQLDatabase sqlDatabase = new SQLDatabase();
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
    public void initialize() {
        // Set the background for the VBox
        anchorPane.setBackground(new Background(background));
    }
    @FXML
    public void superUser() {
       // if(x==false) {
            this.table.getItems().clear();
            this.table.getColumns().clear();
            sqlDatabase.configureTableColumns(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabase());
           // x=true;
            this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.table.setFixedCellSize(25); // Set an appropriate value based on your content
        this.table.prefHeightProperty().bind(Bindings.size(this.table.getItems()).multiply(this.table.getFixedCellSize()).add(30)); // Add some padding
        // }
    }
    @FXML
    public void onDeleteButton(){
        Users user = new Users(userName.getText(),userPassword.getText());
        SQLDatabase sqlDatabase = new SQLDatabase();
        if(userPassword.getText().equals(userPassword2.getText())){
            if(sqlDatabase.deleteUser(user.getUser_name())==false)
                text.setText("Not able to delete this user.");
            else text.setText("Delete Succesfull.");
        } else text.setText("Paswords not matching.");
    }
    @FXML
    public void onInsertButton(){
        Users user = new Users(userName.getText(),userPassword.getText());
        SQLDatabase sqlDatabase = new SQLDatabase();
        if(userPassword.getText().equals(userPassword2.getText())){
           if(sqlDatabase.registerUser(user.getUser_name(),user.getPassword())==false)
                text.setText("Not able to insert this user.");
            else text.setText("Insertion Succesfull.");
        } else text.setText("Paswords not matching.");
    }
    @FXML
    public void onUpdateButton(){
        Users user = new Users(userName.getText(),userPassword.getText());
        SQLDatabase sqlDatabase = new SQLDatabase();
        if(userPassword.getText().equals(userPassword2.getText())){
            if(sqlDatabase.updatePassword(user.getUser_name(),user.getPassword())==false)
                text.setText("Not able to update this user.");
            else text.setText("Update Succesfull.");
        } else text.setText("Paswords not matching.");
    }
    @FXML
    protected void onBackInButtonClick(){
        x=false;
        this.table.getItems().clear();
        this.table.getColumns().clear();
        sqlDatabase.fetchDataFromDatabase().clear();
        this.changeScene(SCENE_IDENTIFIER.LOGIN);
    }
}
