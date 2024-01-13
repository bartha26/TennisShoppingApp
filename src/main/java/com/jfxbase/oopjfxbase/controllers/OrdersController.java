package com.jfxbase.oopjfxbase.controllers;

import com.jfxbase.oopjfxbase.utils.SQLDatabase;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class OrdersController extends SceneController {
    @FXML
    private Button back;
    @FXML
    private Button showOrders;
    @FXML
    private Button showProducts;
    @FXML
    private AnchorPane anchorPane = new AnchorPane();
    @FXML
    private TextField orderNumber;
    @FXML
    private TableView table;
    private SQLDatabase sqlDatabase = new SQLDatabase();
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
    protected void onBackButtonClick(){
        clear();
        orderNumber.clear();
        this.changeScene(SCENE_IDENTIFIER.HELLO);
    }
    @FXML
    protected void onShowOrderButtonClick(){
        clear();
        sqlDatabase.configureTableColumnsOrders(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseOrders());
        configureTables();
    }
    @FXML
    protected void onShowProductsButtonClick(){
        clear();
        sqlDatabase.configureTableColumnsProductsOrder(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseProductsOrder(Integer.parseInt(this.orderNumber.getText())));
        configureTables();
    }
    private void clear(){
        this.table.getItems().clear();
        this.table.getColumns().clear();
    }
    private void configureTables() {
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.table.setFixedCellSize(25); // Set an appropriate value based on your content
        this.table.prefHeightProperty().bind(Bindings.size(this.table.getItems()).multiply(this.table.getFixedCellSize()).add(30));
    }
}

