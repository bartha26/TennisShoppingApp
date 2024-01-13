package SimpleClasses;

import java.util.ArrayList;

public class ShoppingCart extends Item{
    private Double totalPrice;
    private Integer nrProducts;
    private ArrayList<Item> items = new ArrayList<>();
    ShoppingCart(){
        this.nrProducts = 0;
        this.totalPrice = 0.0;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNrProducts() {
        return nrProducts;
    }

    public void setNrProducts(Integer nrProducts) {
        this.nrProducts = nrProducts;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    public void removeItemByName(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProductName().equalsIgnoreCase(itemName)) {
                Item removedItem = items.remove(i);
                updateTotalPriceAfterRemoval(removedItem);
                break;
            }
        }
    }

    private void updateTotalPriceAfterRemoval(Item removedItem) {
        double itemTotalPrice = removedItem.getPrice() * Integer.parseInt(removedItem.getQuantity());
        this.totalPrice -= itemTotalPrice;
        this.nrProducts--;
    }
}
