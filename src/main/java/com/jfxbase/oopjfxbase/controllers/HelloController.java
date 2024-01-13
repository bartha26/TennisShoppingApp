package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.Item;
import SimpleClasses.ShoppingCart;
import SimpleClasses.ShoppingCartManager;
import SimpleClasses.UserManager;
import com.jfxbase.oopjfxbase.utils.SQLDatabase;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import com.jfxbase.oopjfxbase.utils.SceneController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class HelloController extends SceneController {
    @FXML
    private Button back;
    @FXML
    private Button shoes;
    @FXML
    private Button shirts;
    @FXML
    private Button shorts;
    @FXML
    private Button rackets;
    @FXML
    private Button reviews;
    @FXML
    private Button seeCart;
    @FXML
    private Button formerOrders;
    @FXML
    private Button addToCart;
    private Integer index = 0;
    private Integer minPrice;
    private Integer maxPrice;
    private SQLDatabase sqlDatabase4 = new SQLDatabase();
    private SQLDatabase sqlDatabase = new SQLDatabase();
    private SQLDatabase sqlDatabase2 = new SQLDatabase();
    private SQLDatabase sqlDatabase3 = new SQLDatabase();
    @FXML
    private Text message;
    @FXML
    private TextField nameField;
    @FXML
    private TextField type;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField sizeField;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;
    @FXML
    private TableView tableSecond;
    @FXML
    private TableView tableThird;
    private static final String QUERY_EXISTS_RACKETS="SELECT p.product_name, p.price, s2.quantity, p.product_id, ss.wheight_id, c.color_name FROM products p JOIN rackets s ON s.rackets_id = p.product_id JOIN stockracket s2 ON s2.racket_id = s.rackets_id JOIN racketweight ss ON ss.wheight_id = s2.weight_id JOIN productcolor p2 ON p2.product_id = p.product_id JOIN color c ON c.color_id = p2.color_id WHERE p.product_name = ? AND s2.quantity >= ? AND ss.weight = ? AND c.color_name = ?";
    private static final String QUERY_EXISTS_SHORTS="select p.product_name,p.price,s2.quantity,p.product_id, ss.size_id, c.color_name from products p join shorts s ON s.short_id = p.product_id join stockshorts s2 on s2.short_id = s.short_id join shortsize ss on ss.size_id = s2.size_id join productcolor p2 on p2.product_id = p.product_id join color c ON c.color_id = p2.color_id where p.product_name = ? and s2.quantity >= ? and ss.size = ? and c.color_name = ?";
    private static final String QUERY_EXISTS_SHOES="SELECT p.product_name, p.price, s2.quantity, p.product_id, ss.size_id, c.color_name FROM products p JOIN shoes s ON s.shoes_id = p.product_id JOIN stockshoes s2 ON s2.shoes_id = s.shoes_id JOIN shoesize ss ON ss.size_id = s2.size_id JOIN productcolor p2 ON p2.product_id = p.product_id JOIN color c ON c.color_id = p2.color_id WHERE p.product_name = ? AND s2.quantity >= ? AND ss.size = ? AND c.color_name = ?";
    private static final String QUERY_EXISTS_SHIRT="SELECT p.product_name, p.price, s2.quantity, p.product_id, ss.size_id, c.color_name FROM products p JOIN shirts s ON s.shirt_id = p.product_id JOIN stockshirt s2 ON s2.shirt_id = s.shirt_id JOIN size_shirt ss ON ss.size_id = s2.size_id JOIN productcolor p2 ON p2.product_id = p.product_id JOIN color c ON c.color_id = p2.color_id WHERE p.product_name = ? AND s2.quantity >= ? AND ss.size = ? AND c.color_name = ?";
    private static final String QUERY_WEIGHT_RACKET="select * from racketweight r join stockracket s3 on r.wheight_id = s3.weight_id join products p on p.product_id = s3.racket_id where p.price <= ? and p.price >= ?";
    private static final String QUERY_SIZE_SHOES="select * from shoesize s3 join stockshoes s4 on s4.size_id  = s3.size_id join products p on p.product_id = s4.shoes_id where p.price <= ? and p.price >= ?";
    private static final String QUERY_SIZE_SHORT="select * from shortsize s4  join stockshorts s5 on s4.size_id  = s5.size_id join products p on p.product_id = s5.short_id where p.price <= ? and p.price >= ?";
    private static final String QUERY_SIZE_SHIRT ="select * from size_shirt ss join stockshirt s3 on ss.size_id = s3.size_id join products p on p.product_id = s3.shirt_id where p.price <= ? and p.price >= ? ";
    private static final String QUERY_COLOR_SHIRT="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select s2.shirt_id from shirts s2 where p.price <= ? and p.price >= ?)";
    private static final String QUERY_COLOR_SHOES ="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select s2.shoes_id from shoes s2 where p.price <= ? and p.price >= ?)";
    private static final String QUERY_COLOR_SHORTS="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select s2.short_id from shorts s2 where p.price <= ? and p.price >= ?)";
    private static final String QUERY_COLOR_RACKETS="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select r.rackets_id from rackets r where p.price <= ? and p.price >= ?)";
    private static final String QUERY_SHIRT = "select * from products p join shirts s on p.product_id = s.shirt_id join company c on c.company_id = p.company_id  where p.price >= ? and p.price <= ?";
    private static final String QUERY_SHORTS="select * from products p join shorts s on p.product_id = s.short_id join company c on c.company_id = p.company_id  where p.price >= ? and p.price <= ?";
    private static final String QUERY_SHOES = "select * from products p join shoes s on p.product_id = s.shoes_id join company c on c.company_id = p.company_id  where p.price >= ? and p.price <= ?";
    private static final String QUERY_RACKETS = "select * from products p join rackets r on r.rackets_id =p.product_id join company c on c.company_id =p.company_id  where p.price >= ? and p.price <= ?";
    private static final String QUERY_NAME_SIZE_RACKET="select * from racketweight r join stockracket s3 on r.wheight_id = s3.weight_id join products p on p.product_id = s3.racket_id where p.product_name = ?";
    private static final String QUERY_NAME_SIZE_SHOES="select * from shoesize s3 join stockshoes s4 on s4.size_id  = s3.size_id join products p on p.product_id = s4.shoes_id where p.product_name = ?";
    private static final String QUERY_NAME_SIZE_SHIRT="select * from size_shirt ss join stockshirt s3 on ss.size_id = s3.size_id join products p on p.product_id = s3.shirt_id where p.product_name = ?";
    private static final String QUERY_NAME_SIZE_SHORTS="select * from shortsize s4  join stockshorts s5 on s4.size_id  = s5.size_id join products p on p.product_id = s5.short_id where p.product_name = ?";
    private static final String QUERY_NAME_COLOR_SHIRT="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select s2.shirt_id from shirts s2 where p.product_name = ?)";
    private static final String QUERY_NAME_COLOR_SHOES ="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select s2.shoes_id from shoes s2 where p.product_name = ?)";
    private static final String QUERY_NAME_COLOR_SHORTS="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select s2.short_id from shorts s2 where p.product_name = ?)";
    private static final String QUERY_NAME_COLOR_RACKETS="select * from productcolor p2 join color c2 on c2.color_id = p2.color_id join products p on p.product_id = p2.product_id where p2.product_id in (select r.rackets_id from rackets r where p.product_name = ?)";

    @FXML
    private TableView table;
    private Image backgroundImage = new Image("C:\\Users\\TudorB\\Desktop\\hello2.jpg");
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
        anchorPane.setBackground(new Background(background));
        System.out.println("bck");
    }
    @FXML
    protected void onBackButtonClick() {this.changeScene(SCENE_IDENTIFIER.LOGIN);}
    @FXML
    protected void onShoesButtonClick(){
        price();
        clear();
        if(nameField.getText().isEmpty()) {
            sqlDatabase.configureTableColumnsShoes(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseShoes(QUERY_SHOES, minPrice, maxPrice));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColor(QUERY_COLOR_SHOES, minPrice,maxPrice));

            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSize(QUERY_SIZE_SHOES, "shoes_id", "size", minPrice, maxPrice));
        }else{
            sqlDatabase.configureTableColumnsShoes(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseShoesName(nameField.getText()));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColorName(QUERY_NAME_COLOR_SHOES, nameField.getText()));

            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSizeName(QUERY_NAME_SIZE_SHOES, "shoes_id", "size", nameField.getText()));
        }
        configureTables();
    }
    @FXML
    protected void onShirtButtonClick(){
        price();
        clear();
        if(nameField.getText().isEmpty()) {
            sqlDatabase.configureTableColumnsShirt(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseShirt(QUERY_SHIRT, minPrice, maxPrice));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColor(QUERY_COLOR_SHIRT, minPrice,maxPrice));

            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSize(QUERY_SIZE_SHIRT, "shirt_id", "size", minPrice, maxPrice));
        }else{
            sqlDatabase.configureTableColumnsShirt(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseShirtName(nameField.getText()));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColorName(QUERY_NAME_COLOR_SHIRT, nameField.getText()));

            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSizeName(QUERY_NAME_SIZE_SHIRT, "shirt_id", "size", nameField.getText()));

        }
        configureTables();
    }
    @FXML
    protected void onShortsButtonClick(){
        price();
        clear();
        if(nameField.getText().isEmpty()) {
            sqlDatabase.configureTableColumnsShorts(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseShorts(QUERY_SHORTS, minPrice, maxPrice));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColor(QUERY_COLOR_SHORTS, minPrice,maxPrice));

            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSize(QUERY_SIZE_SHORT, "short_id", "size", minPrice, maxPrice));
        } else{
            sqlDatabase.configureTableColumnsShorts(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseShortsName(nameField.getText()));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColorName(QUERY_NAME_COLOR_SHORTS, nameField.getText()));

            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSizeName(QUERY_NAME_SIZE_SHORTS, "short_id", "size", nameField.getText()));

        }
        configureTables();
    }
    @FXML
    protected void onRacketsButtonClick(){
        price();
        clear();
        if(nameField.getText().isEmpty()) {
            sqlDatabase.configureTableColumnsRackets(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseRackets(QUERY_RACKETS, minPrice, maxPrice));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColor(QUERY_COLOR_RACKETS, minPrice,maxPrice));


            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSize(QUERY_WEIGHT_RACKET, "racket_id", "weight", minPrice, maxPrice));
          //  System.out.println(minPrice + "  " + maxPrice);
        }
        else {
            sqlDatabase.configureTableColumnsRackets(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseRacketsName(nameField.getText()));

            sqlDatabase2.configureTableColumnsColor(this.tableSecond);
            this.tableSecond.setItems(sqlDatabase2.fetchDataFromDatabaseColorName(QUERY_NAME_COLOR_RACKETS, nameField.getText()));

            sqlDatabase3.configureTableColumnsSize(this.tableThird);
            this.tableThird.setItems(sqlDatabase3.fetchDataFromDatabaseSizeName(QUERY_NAME_SIZE_RACKET, "racket_id", "weight", nameField.getText()));

        }
       configureTables();
    }
    @FXML
    protected void onReviewButtonClick(){
        clear();
        this.changeScene(SCENE_IDENTIFIER.REVIEW);
        message.setText("HELLO!");
    }
    @FXML
    protected void onAddToCartButtonClick(){
        boolean flag = false;
        if (!nameField.getText().isEmpty() && !quantityField.getText().isEmpty() && !colorField.getText().isEmpty() && isNumeric(quantityField.getText()) && !sizeField.getText().isEmpty() && Integer.parseInt(quantityField.getText()) > 0) {
            for (Item item:ShoppingCartManager.getInstance().getShoppingCart().getItems()) {
                if(item.getProductName().equals(nameField.getText()) && item.getColor().equals(colorField.getText())) {
                    ShoppingCartManager.getInstance().getShoppingCart().setTotalPrice(ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice() + item.getPrice() * Integer.parseInt(quantityField.getText()));
                    item.setQuantity(Integer.toString(Integer.parseInt(item.getQuantity()) + Integer.parseInt(quantityField.getText())));
                    message.setText("Addition successful");
                    return;
                }
            }
            String typeText = type.getText();
            switch (typeText) {
                case "racket":
                   flag = sqlDatabase4.updateShoppingCart( nameField.getText(),quantityField.getText(),sizeField.getText(),colorField.getText(),QUERY_EXISTS_RACKETS,"wheight_id",typeText);
                    break;

                case "shoe":
                  flag =  sqlDatabase4.updateShoppingCart( nameField.getText(),quantityField.getText(),sizeField.getText(),colorField.getText(),QUERY_EXISTS_SHOES,"size_id",typeText);
                    break;

                case "shirt":
                 flag =  sqlDatabase4.updateShoppingCart( nameField.getText(),quantityField.getText(),sizeField.getText(),colorField.getText(),QUERY_EXISTS_SHIRT,"size_id",typeText);
                    break;

                case "short":
                   flag = sqlDatabase4.updateShoppingCart( nameField.getText(),quantityField.getText(),sizeField.getText(),colorField.getText(),QUERY_EXISTS_SHORTS,"size_id",typeText);
                    break;
                default:
                    message.setText("Wrong type!");
            }
        } else {
            message.setText("Please give valid inputs!");
        }
        if(flag == true){
            message.setText("Product added");
        } else message.setText("Product not available");
        //amount.setText("Current total price: "+ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice()+" RON");
    }
    @FXML
    protected void onSeeFormerOrders(){
        this.changeScene(SCENE_IDENTIFIER.ORDERS);
    }
    @FXML
    protected void onSeeCartButtonClick(){
        this.changeScene(SCENE_IDENTIFIER.CART);
    }
    private void price(){
        if(minPriceField.getText().isEmpty()){
            minPrice = 0;
        } else minPrice = Integer.parseInt(minPriceField.getText());
        if(maxPriceField.getText().isEmpty()){
            maxPrice = 10000000;
        } else maxPrice = Integer.parseInt(maxPriceField.getText());
    }
    private void clear(){
        this.table.getItems().clear();
        this.table.getColumns().clear();
        this.tableSecond.getItems().clear();
        this.tableSecond.getColumns().clear();
        this.tableThird.getItems().clear();
        this.tableThird.getColumns().clear();
        this.minPriceField.clear();
        this.maxPriceField.clear();
       // this.nameField.clear();
    }
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void configureTables(){
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.table.setFixedCellSize(25); // Set an appropriate value based on your content
        this.table.prefHeightProperty().bind(Bindings.size(this.table.getItems()).multiply(this.table.getFixedCellSize()).add(30));
      //  this.tableSecond.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      //  this.tableSecond.setFixedCellSize(25); // Set an appropriate value based on your content
       // this.tableSecond.prefHeightProperty().bind(Bindings.size(this.tableSecond.getItems()).multiply(this.tableSecond.getFixedCellSize()).add(30));
      //  this.tableThird.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      //  this.tableThird.setFixedCellSize(25); // Set an appropriate value based on your content
      //  this.tableThird.prefHeightProperty().bind(Bindings.size(this.tableThird.getItems()).multiply(this.tableThird.getFixedCellSize()).add(30));
    }
}