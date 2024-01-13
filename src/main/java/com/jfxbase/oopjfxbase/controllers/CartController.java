package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.Item;
import SimpleClasses.Products;
import SimpleClasses.ShoppingCart;
import SimpleClasses.ShoppingCartManager;
import SimpleClasses.Item;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;

public class CartController extends SceneController {
    @FXML
    private Button back;
    @FXML
    private Button delete;
    @FXML
    private Button modify;
    @FXML
    private Button showData;
    @FXML
    private Button pay;
    @FXML
    private AnchorPane anchorPane = new AnchorPane();
    @FXML
    private TextField quantityField;
    @FXML
    private TextField deleteField;
    @FXML
    private TableView table;
    @FXML
    private Text amount;
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
        this.changeScene(SCENE_IDENTIFIER.HELLO);
    }

    @FXML
    protected void onShowDataButtonClick() {
        clear();
        configureTableColumnsOrder(this.table);
        configureTables();
        amount.setText("Total amount is: " + ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice() + " RON");
    }
    @FXML
    protected void onDeleteButtonClick() {
        String itemNameToDelete = this.deleteField.getText();
        if (!itemNameToDelete.isEmpty()) {
            deleteItem(itemNameToDelete);
        }
        amount.setText("Total amount is: " + ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice() + " RON");
    }
    @FXML
    protected void onModifyButtonClick() {
        String itemNameToModify = deleteField.getText();
        String newQuantity = quantityField.getText();

        if (!itemNameToModify.isEmpty() && !newQuantity.isEmpty()) {
            if(Integer.parseInt(quantityField.getText()) == 0)
                deleteItem(itemNameToModify);
            else {
                try {
                    modifyItemQuantity(itemNameToModify, newQuantity);
                } catch (NumberFormatException e) {
                    // Handle invalid quantity input (not an integer)
                    e.printStackTrace(); // You might want to log or display an error message
                }
            }
        }
        amount.setText("Total amount is: " + ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice() + " RON");
    }
    @FXML
    private void onPayButtonClick(){
        this.changeScene(SCENE_IDENTIFIER.PAY);
        clear();
        this.quantityField.clear();
        this.deleteField.clear();
    }

    private void modifyItemQuantity(String itemName, String newQuantity) {
        ShoppingCart shoppingCart = ShoppingCartManager.getInstance().getShoppingCart();
        ObservableList<Item> items = FXCollections.observableArrayList();
        items.addAll(shoppingCart.getItems());

        // Find the item with the specified name
        for (Item item : items) {
            if (item.getProductName().equalsIgnoreCase(itemName)) {
                Integer oldQuantity = Integer.parseInt(item.getQuantity());
                // Update the quantity
                item.setQuantity(newQuantity);
                ShoppingCartManager.getInstance().getShoppingCart().setTotalPrice( ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice() + item.getPrice()* (Integer.parseInt(item.getQuantity()) - oldQuantity));
                break; // Assuming each item has a unique name, so we can break out of the loop
            }
        }

        // Clear and reconfigure the table after modification
        clear();
        configureTableColumnsOrder(this.table);
    }
    private void deleteItem(String itemName) {
        ShoppingCartManager.getInstance().getShoppingCart().removeItemByName(itemName);
        // Clear and reconfigure the table after deletion
        clear();
        configureTableColumnsOrder(this.table);
    }


    public void configureTableColumnsOrder(TableView<Item> tableView) {
        ObservableList<Item> shoppingCartList = FXCollections.observableArrayList();
        shoppingCartList.addAll(ShoppingCartManager.getInstance().getShoppingCart().getItems());

        // Create columns for product name, price, quantity, and size
        TableColumn<Item, String> productNameColumn = new TableColumn<>("Product Name");
        for (Item items:ShoppingCartManager.getInstance().getShoppingCart().getItems()) {
            productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
            productNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        for (Item items:ShoppingCartManager.getInstance().getShoppingCart().getItems()) {
            priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
            priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        }

        TableColumn<Item, String> quantityColumn = new TableColumn<>("Quantity");
        for (Item items:ShoppingCartManager.getInstance().getShoppingCart().getItems()) {
            quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity()));
            quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }

        TableColumn<Item, String> sizeColumn = new TableColumn<>("Size");
        for (Item items:ShoppingCartManager.getInstance().getShoppingCart().getItems()) {
            sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSize()));
            sizeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }
        TableColumn<Item, String> productColorColumn = new TableColumn<>("Product Color");
        for (Item items:ShoppingCartManager.getInstance().getShoppingCart().getItems()) {
            productColorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
            productColorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }

        // Set the columns to the TableView
        tableView.getColumns().addAll(
                productNameColumn,
                priceColumn,
                quantityColumn,
                sizeColumn,
                productColorColumn
        );

        // Set the items to the TableView
        tableView.setItems(shoppingCartList);
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
