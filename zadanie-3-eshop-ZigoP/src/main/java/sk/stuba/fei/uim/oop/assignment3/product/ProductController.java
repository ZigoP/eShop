package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return this.service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }
    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(new ProductResponse(this.service.create(productRequest)), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Long id){
        return this.service.getById(id).map(ProductResponse::new).orElseThrow();
    }
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest){
        Product updateProduct = this.service.getById(id).orElseThrow();
        return new ProductResponse(this.service.update(updateProduct, productRequest.getName(), productRequest.getDescription()));
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        Product deleteProduct = this.service.getById(id).orElseThrow();
        this.service.delete(deleteProduct);
    }
    @GetMapping("/{id}/amount")
    public HashMap<String, Integer> getAmount(@PathVariable("id") Long id){
        Product product = this.service.getById(id).orElseThrow();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("amount", product.getAmount());
        return map;
    }
    @PostMapping("/{id}/amount")
    public HashMap<String, Integer> increaseAmount(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest){
        Product product = this.service.getById(id).orElseThrow();
        HashMap<String, Integer> map = new HashMap<>();
        product = this.service.increase(product, productRequest.getAmount());
        map.put("amount", product.getAmount());
        return map;
    }


}