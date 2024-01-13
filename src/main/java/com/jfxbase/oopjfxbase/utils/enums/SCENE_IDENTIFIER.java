package com.jfxbase.oopjfxbase.utils.enums;

public enum SCENE_IDENTIFIER {
    LOGIN("login-view.fxml"),
    HELLO("hello-view.fxml"),
    GOOD_BYE("good-bye-view.fxml"),
    SIGN_IN("sign-in-view.fxml"),
    SUPER_USER("super-user-view.fxml"),
    REVIEW("review-view.fxml"),
    CART("cart-view.fxml"),
    EPILOGUE("epilogue-view.fxml"),
    ORDERS("orders-view.fxml"),
    PAY("pay-view.fxml");

    public final String label;

    SCENE_IDENTIFIER(String label) {
        this.label = label;
    }
}
