package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService service;

    @PostMapping
    public ResponseEntity<CartResponse> createCart() {
        return new ResponseEntity<>(new CartResponse(this.service.create()), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public CartResponse getCart(@PathVariable("id") Long id){
        return this.service.getById(id).map(CartResponse::new).orElseThrow();
    }
    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable("id") Long id){
        Cart deleteCart = this.service.getById(id).orElseThrow();
        this.service.delete(deleteCart);
    }

}
