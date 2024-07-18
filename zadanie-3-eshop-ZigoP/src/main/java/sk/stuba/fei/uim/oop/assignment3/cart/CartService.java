package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.product.IProductService;
import sk.stuba.fei.uim.oop.assignment3.product.Product;
import sk.stuba.fei.uim.oop.assignment3.product.ProductInfo;

import java.util.Optional;

@Service
public class CartService implements ICartService{

    private ICartRepository repository;

    @Autowired
    public CartService(ICartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cart create() {
        return this.repository.save(new Cart());
    }

    @Override
    public Optional<Cart> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Cart cart) {
        this.repository.delete(cart);
    }

    @Override
    public ResponseEntity<Cart> addProduct(Cart cart, ProductInfo productInfo, IProductService productService)  {
        boolean inCart = false;
        if(cart.isPayed()){
            return new ResponseEntity<>((cart), HttpStatus.BAD_REQUEST);
        }
        for(ProductInfo pr : cart.getShoppingList()){
            if(pr.getProductId().equals(productInfo.getProductId())){
                inCart = true;
                Product product = productService.getById(productInfo.getProductId()).orElseThrow();
                if(product.getAmount()- productInfo.getAmount() < 0){
                    return new ResponseEntity<>((cart), HttpStatus.BAD_REQUEST);
                }
                else{
                    pr.setAmount(pr.getAmount() + productInfo.getAmount());
                    product.setAmount(product.getAmount() - productInfo.getAmount());
                    productService.save(product);

                }
            }
        }
        if(!inCart){
            Product product = productService.getById(productInfo.getProductId()).orElseThrow();
            if(product.getAmount() - productInfo.getAmount() < 0){
                return new ResponseEntity<>((cart), HttpStatus.BAD_REQUEST);
            }else{
                cart.getShoppingList().add(productInfo);
                product.setAmount(product.getAmount()- productInfo.getAmount());
                productService.save(product);
            }
        }
        return new ResponseEntity<>((cart), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> pay(Cart cart, IProductService productService) {
        double sum = 0.0;
        for(ProductInfo p : cart.getShoppingList()){
            double currSum = productService.getById(p.getProductId()).orElseThrow().getPrice() * p.getAmount();
            sum += currSum;
        }
        if(cart.isPayed()){
            return new ResponseEntity<>((""+sum),HttpStatus.BAD_REQUEST);
        }
        cart.setPayed(true);
        return new ResponseEntity<>((""+sum),HttpStatus.OK);
    }

}
