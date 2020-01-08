package org.fasttrackit.feedbackapplication.service;

import org.fasttrackit.feedbackapplication.domain.Product;
import org.fasttrackit.feedbackapplication.exception.ResourceNotFoundException;
import org.fasttrackit.feedbackapplication.persistance.ProductRepository;
import org.fasttrackit.feedbackapplication.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request){
        LOGGER.info("Creating product {}", request);
        Product product = new Product();
        product.setName(request.getName());

        return productRepository.save(product);
    }

    public Product getProduct(long id) {

        LOGGER.info("Retrieving product {}", id);
        //using optional
        return productRepository.findById(id)
                //lambda expression
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product " + id + " does not exist."));
    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {}: {}", id, request);

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);

    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);

    }
}
