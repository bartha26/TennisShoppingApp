package SimpleClasses;

import java.util.ArrayList;

public class ShoppingCartManager {
    private static ShoppingCartManager instance;
    private ShoppingCart shoppingCart = new ShoppingCart();
    ShoppingCartManager(){
     //   this.shoppingCarts = new ShoppingCart();
    }
    public static synchronized ShoppingCartManager getInstance(){
        if(instance == null)
            instance = new ShoppingCartManager();
        return instance;
    }

    public static void setInstance(ShoppingCartManager instance) {
        ShoppingCartManager.instance = instance;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
