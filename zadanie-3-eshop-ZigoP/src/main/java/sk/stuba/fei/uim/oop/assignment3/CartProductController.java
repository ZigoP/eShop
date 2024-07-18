package sk.stuba.fei.uim.oop.assignment3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.cart.Cart;
import sk.stuba.fei.uim.oop.assignment3.cart.CartResponse;
import sk.stuba.fei.uim.oop.assignment3.cart.ICartService;
import sk.stuba.fei.uim.oop.assignment3.product.IProductService;
import sk.stuba.fei.uim.oop.assignment3.product.ProductInfo;

@RestController
public class CartProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICartService cartService;

    @PostMapping("/cart/{id}/add")
    public ResponseEntity<CartResponse> addProductToCart(@PathVariable("id") Long id, @RequestBody ProductInfo productInfo){
        Cart cart = this.cartService.getById(id).orElseThrow();
        ResponseEntity<Cart> responseEntity = this.cartService.addProduct(cart, productInfo, productService);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return new ResponseEntity<>(new CartResponse(responseEntity.getBody()), HttpStatus.OK);

        }
        return new ResponseEntity<>(new CartResponse(responseEntity.getBody()), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cart/{id}/pay")
    public ResponseEntity<String> payForCart(@PathVariable("id") Long id){
        Cart cart = this.cartService.getById(id).orElseThrow();
        return this.cartService.pay(cart, productService);
    }
}
