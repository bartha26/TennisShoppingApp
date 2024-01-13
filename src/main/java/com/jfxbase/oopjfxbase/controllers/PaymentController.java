package com.jfxbase.oopjfxbase.controllers;

import SimpleClasses.Item;
import SimpleClasses.ShoppingCartManager;
import SimpleClasses.UserManager;
import com.jfxbase.oopjfxbase.utils.SQLDatabase;
import com.jfxbase.oopjfxbase.utils.SceneController;
import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.Date;
import java.time.LocalDate;

public class PaymentController extends SceneController {
    @FXML
    private Button back;
    @FXML
    private Button finishPayment;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField name;
    @FXML
    private TextField cvv;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField shippingAddress;
    @FXML
    private Text message;
    private static final String UPDATE_QUANTITY_SHOES = "UPDATE stockshoes SET quantity = GREATEST(quantity - ?, 0) WHERE shoes_id = ? AND size_id = ?";
    private static final String UPDATE_QUANTITY_SHIRT = "UPDATE stockshirt SET quantity = GREATEST(quantity - ?, 0) WHERE shirt_id = ? AND size_id = ?";
    private static final String UPDATE_QUANTITY_SHORT = "UPDATE stockshorts SET quantity = GREATEST(quantity - ?, 0) WHERE short_id = ? AND size_id = ?";
    private static final String UPDATE_QUANTITY_RACKET = "UPDATE stockracket SET quantity = GREATEST(quantity - ?, 0) WHERE racket_id = ? AND weight_id = ?";
    private static final String FIND_SIZEID_SHIRT = "select ss.size_id from size_shirt ss where ss.size = ?";
    private static final String FIND_SIZEID_SHORT = "select s.size_id from shortsize s where s.size = ?";
    private static final String FIND_SIZEID_SHOE = "select s.size_id from shoesize s where s.size = ?";
    private static final String FIND_SIZEID_RACKET = "select r.wheight_id from racketweight r where r.weight = ?";
    private SQLDatabase sqlDatabase;
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
        this.changeScene(SCENE_IDENTIFIER.CART);
        clear();
    }
    @FXML
    protected void onFinishPayButtonClick(){
        if (this.cardNumber.getText().matches("\\d{16}") && this.cvv.getText().matches("\\d{3}") && this.name.getText().matches("^[A-Za-z]+\\s[A-Za-z]+$")){
            if( this.shippingAddress.getText().matches("^[A-Za-z\\s]+$") && !this.shippingAddress.getText().isEmpty()){
                if(this.datePicker.getValue().isAfter(LocalDate.now())) {
                    Integer userId = UserManager.getInstance().getCurrentUser().getId();
                    Double price = ShoppingCartManager.getInstance().getShoppingCart().getTotalPrice();
                    Integer orderId = sqlDatabase.insertOrder(userId, price, this.shippingAddress.getText());
                    for (Item item : ShoppingCartManager.getInstance().getShoppingCart().getItems()) {
                       // System.out.println("yes");
                        Integer colorId = sqlDatabase.findColorId(item.getColor());
                        sqlDatabase.insertProductOrder(item.getProductId(), orderId, Integer.parseInt(item.getQuantity()), colorId);
                        Integer sizeId=Integer.parseInt(item.getSize());
                        System.out.println(item.getType());
                        switch (item.getType()) {
                            case "racket":
                                sqlDatabase.updateQuantity(Integer.parseInt(item.getQuantity()), item.getProductId(), sizeId, UPDATE_QUANTITY_RACKET);//write query for size using the type
                                break;
                            case "shoe":
                                sqlDatabase.updateQuantity(Integer.parseInt(item.getQuantity()), item.getProductId(), sizeId, UPDATE_QUANTITY_SHOES);//write query for size using the type
                                break;
                            case "shirt":
                                sqlDatabase.updateQuantity(Integer.parseInt(item.getQuantity()), item.getProductId(), sizeId, UPDATE_QUANTITY_SHIRT);//write query for size using the type
                                break;
                            case "short":
                                sqlDatabase.updateQuantity(Integer.parseInt(item.getQuantity()), item.getProductId(), sizeId, UPDATE_QUANTITY_SHORT);//write query for size using the type
                                break;
                            default:
                                System.out.println("none of the categories above");
                                break;
                        }
                        System.out.println(sizeId);
                    }
                    this.changeScene(SCENE_IDENTIFIER.EPILOGUE);
                }else this.message.setText("dateNotOk");
            }else this.message.setText("Shipping address incorrect");
        } else this.message.setText("Card detalis incorrect");
    }
    private void clear(){
        this.cvv.clear();
        this.cardNumber.clear();
        this.shippingAddress.clear();
        this.name.clear();
    }

}
