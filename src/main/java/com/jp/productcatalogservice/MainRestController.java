package com.jp.productcatalogservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class MainRestController {


    @Autowired
    ProductRepository productRepo;

    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    @GetMapping("/test")
    public ResponseEntity<?> testProductService() {
        return ResponseEntity.ok("Product Service running fine");
    }

    @PostMapping("/products/add")
    public ResponseEntity<?> createPlan(@RequestBody Product product)
    {
        log.info("Received request to add product : {}", product);
        productRepo.save(product);
        return ResponseEntity.ok("New Product Added.");
    }

    @GetMapping("/products/view/{productId}")
    public ResponseEntity<?> viewProduct(@PathVariable Long productId){
        log.info("Received request to view product with product id : {} ",productId);
        Optional<Product> productObj = productRepo.findById(productId);
        if(productObj.isPresent()){
            return ResponseEntity.ok(productObj);
        }else{
            return  ResponseEntity.ok("No product found with given id : "+productId);
        }
    }

    @PostMapping("/products/update")
    public ResponseEntity<?> updateProductDetails(@RequestBody Product productParam){
        log.info("Received request to update product with data : {} ",productParam);

        Optional<Product> productObj = productRepo.findById(productParam.getProductId());
        if(productObj.isPresent()){
            productRepo.save(productParam);
            return ResponseEntity.ok("Product data updated successfully.");
        }else{
            return  ResponseEntity.ok("No product found with given id : "+(productParam.getProductId()));
        }
    }

    @PostMapping("/products/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        log.info("Received request to delete product with product id : {} ",productId);
        log.info("Testing extra");
        Optional<Product> productObj = productRepo.findById(productId);
        if(productObj.isPresent()){
            productRepo.deleteById(productId);
            return ResponseEntity.ok("Product data deleted successfully.");
        }else{
            return  ResponseEntity.ok("No product found with given id : "+productId);
        }
    }

    @GetMapping("products/viewAll")
    public ResponseEntity<?> viewAllProducts(){
        log.info("Received request to view all products");
        return ResponseEntity.ok(productRepo.findAll());
    }


}
