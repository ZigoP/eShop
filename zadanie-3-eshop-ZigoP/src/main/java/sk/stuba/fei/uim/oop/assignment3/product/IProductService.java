package sk.stuba.fei.uim.oop.assignment3.product;

import java.util.List;
import java.util.Optional;

public interface IProductService{

    List<Product> getAll();
    Product create(ProductRequest productRequest);
    Optional<Product> getById(Long id);
    Product update(Product product, String name, String description);
    void delete(Product product);
    Product increase(Product product, int amount);
    Product save(Product product);
}
