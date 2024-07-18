package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService implements IProductService{

    private  IProductRepository repository;

    @Autowired
    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product create(ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getAmount(),
                productRequest.getUnit(),
                productRequest.getPrice()
        );
        return this.repository.save(product);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Product update(Product product, String name, String description) {
        if(name!=null){
            product.setName(name);
        }
        if(description!=null){
            product.setDescription(description);
        }
        return this.repository.save(product);
    }

    @Override
    public void delete(Product product) {
        this.repository.delete(product);
    }

    @Override
    public Product increase(Product product, int amount) {
        product.setAmount(product.getAmount() + amount);
        return this.repository.save(product);
    }

    @Override
    public Product save(Product product) {
        return this.repository.save(product);
    }
}