package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.http.ResponseEntity;
import sk.stuba.fei.uim.oop.assignment3.product.IProductService;
import sk.stuba.fei.uim.oop.assignment3.product.ProductInfo;

import java.util.Optional;

public interface ICartService {

    Cart create();
    Optional<Cart> getById(Long id);
    void delete(Cart cart);
    ResponseEntity<Cart> addProduct(Cart cart, ProductInfo productInfo, IProductService productService);
    ResponseEntity<String> pay(Cart cart, IProductService productService);
}
