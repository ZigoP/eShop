package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.product.ProductInfo;
import java.util.List;

@Getter
public class CartResponse {
    private Long id;
    private List<ProductInfo> shoppingList;
    private boolean payed;

    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.shoppingList = cart.getShoppingList();
        this.payed = cart.isPayed();
    }
}
