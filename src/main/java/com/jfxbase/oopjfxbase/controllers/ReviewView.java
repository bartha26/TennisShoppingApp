package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.Review;
import SimpleClasses.UserManager;
import com.jfxbase.oopjfxbase.utils.SQLDatabase;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

public class ReviewView extends SceneController {
    @FXML
    private Button back;
    @FXML
    private Button search;
    @FXML
    private Button shoes;
    @FXML
    private Button shirts;
    @FXML
    private Button rackets;
    @FXML
    private Button shorts;
    @FXML
    private Button addReview;
    @FXML
    private Button searchByRating;
    @FXML
    private Button myReviews;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField product;
    @FXML
    private TextField brand;
    @FXML
    private TableView<Review> table;
    @FXML
    private TextField comments;
    @FXML
    private TextField nameProductReview;
    @FXML
    private TextField rating;
    @FXML
    private Text reviewMessage;
    private SQLDatabase sqlDatabase = new SQLDatabase();
    private Integer before = -1;
    private String arg1 = "";
    private String arg2 = "";

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
    protected void searchByRatingButtonClick() {
        this.table.getItems().clear();
        this.table.getColumns().clear();
        clear(arg1, arg2);
        before = 0;
        sqlDatabase.configureTableColumnsReview(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseRatingReview());
        configureTables();
    }

    @FXML
    protected void searchButtonClick() {
        this.table.getItems().clear();
        this.table.getColumns().clear();

        if (!(product.getText().isEmpty()) && (brand.getText().isEmpty())) {
            clear(arg1, arg2);
            arg1 = product.getText();
            before = 2;
            sqlDatabase.configureTableColumnsReview(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseSpecificProductReview(product.getText()));
        }
        if (!(brand.getText().isEmpty()) && (product.getText().isEmpty())) {
            clear(arg1, arg2);
            arg2 = brand.getText();
            before = 1;
            sqlDatabase.configureTableColumnsReview(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseBrandReview(brand.getText()));
        }
        if (!(product.getText().isEmpty()) && !(brand.getText().isEmpty())) {
            clear(arg1, arg2);
            arg1 = product.getText();
            arg2 = brand.getText();
            before = 3;
            sqlDatabase.configureTableColumnsReview(this.table);
            this.table.setItems(sqlDatabase.fetchDataFromDatabaseBrandProductReview(brand.getText(), product.getText()));
        }
        configureTables();
    }
    @FXML
    protected void onMyReviewsButtonClick(){
        this.table.getItems().clear();
        this.table.getColumns().clear();
        sqlDatabase.configureTableColumnsReview(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseMyReviews(UserManager.getInstance().getCurrentUser().getId()));
        configureTables();
    }

    @FXML
    protected void searchShoesButtonClick() {
        this.table.getItems().clear();
        this.table.getColumns().clear();
        clear(arg1, arg2);
        before = 4;
        sqlDatabase.configureTableColumnsReview(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseShoesReview());
        configureTables();
    }

    @FXML
    protected void searchShirtButtonClick() {
        this.table.getItems().clear();
        this.table.getColumns().clear();
        clear(arg1, arg2);
        before = 5;
        sqlDatabase.configureTableColumnsReview(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseShirtReview());
        configureTables();
    }

    @FXML
    protected void searchShortsButtonClick() {
        this.table.getItems().clear();
        this.table.getColumns().clear();
        clear(arg1, arg2);
        before = 6;
        sqlDatabase.configureTableColumnsReview(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseShortsReview());
        configureTables();
    }

    @FXML
    protected void searchRacketsButtonClick() {
        this.table.getItems().clear();
        this.table.getColumns().clear();
        clear(arg1, arg2);
        before = 7;
        sqlDatabase.configureTableColumnsReview(this.table);
        this.table.setItems(sqlDatabase.fetchDataFromDatabaseRacketsReview());
        configureTables();
    }
    @FXML
    protected void backButtonClick(){
        this.nameProductReview.clear();
        this.rating.clear();
        this.comments.clear();
        this.product.clear();
        this.brand.clear();
        this.reviewMessage.setText("Hello!");
        this.table.getItems().clear();
        this.table.getColumns().clear();
        clear(arg1, arg2);
        before = -1;
        this.changeScene(SCENE_IDENTIFIER.HELLO);
    }
    @FXML
    protected void addReviewButtonClick(){
        SQLDatabase sqlDatabase1 = new SQLDatabase();
        if(nameProductReview.getText().isEmpty() || rating.getText().isEmpty())
            reviewMessage.setText("Please introduce a valid nameProduct and rating");
        else{
            Review review = new Review(Integer.parseInt(rating.getText()), UserManager.getInstance().getCurrentUser().getUser_name(),comments.getText(),nameProductReview.getText(),sqlDatabase1.findProductId(nameProductReview.getText()),sqlDatabase1.findUserId(UserManager.getInstance().getCurrentUser().getUser_name()));
            if(sqlDatabase1.findReview(review.getUserId(),review.getProductId()) != true){
                if (sqlDatabase1.addReview(review) == true) {
                    reviewMessage.setText("Review introduced succesfully");
                    this.rating.clear();
                    this.comments.clear();
                    this.nameProductReview.clear();
                    this.brand.clear();
                }
            } else reviewMessage.setText("You already have reviewed this product!");
        }
    }
    protected void clear(String arg1, String arg2) {
        this.table.getItems().clear();
        this.table.getColumns().clear();
        if (before == 0) {
            sqlDatabase.fetchDataFromDatabaseRatingReview().clear();
        } else if (before == 1) {
            sqlDatabase.fetchDataFromDatabaseBrandReview(arg2).clear();
        } else if (before == 2) {
            sqlDatabase.fetchDataFromDatabaseSpecificProductReview(arg1).clear();
        } else if (before == 3) {
            sqlDatabase.fetchDataFromDatabaseBrandProductReview(arg1, arg2);
        } else if (before == 4) {
            sqlDatabase.fetchDataFromDatabaseShoesReview();
        } else if (before == 5) {
            sqlDatabase.fetchDataFromDatabaseShirtReview();
        } else if (before == 6) {
            sqlDatabase.fetchDataFromDatabaseShortsReview();
        } else if (before == 7) {
            sqlDatabase.fetchDataFromDatabaseRacketsReview();
        }
    }
    private void configureTables() {
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.table.setFixedCellSize(25); // Set an appropriate value based on your content
        this.table.prefHeightProperty().bind(Bindings.size(this.table.getItems()).multiply(this.table.getFixedCellSize()).add(30));
    }
}