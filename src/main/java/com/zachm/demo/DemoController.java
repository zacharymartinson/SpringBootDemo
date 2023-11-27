package com.zachm.demo;

import com.zachm.demo.util.RestJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class DemoController {
    public static List<Product> Products = RestJsonReader.products;
    @GetMapping
    public List<Product> getProducts() {
        //Returns all products
        return Products;
    }
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        //Returns Specific product with a filter by ID
        return Products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }
    @PostMapping
    public boolean addProduct(@RequestBody Product new_product) {
        //TODO Make a json backup for this.
        //Adds a new product.
        if(Products.contains(new_product)) {
            return false;
        }
        else {
            Products.add(new_product);
            return true;
        }
    }
    @PutMapping
    public boolean updateProduct(@RequestBody Product new_product, @PathVariable Long id) {
        //TODO Make a json backup for this
        //Updates specific product by filtering id, I avoid using null here
        Optional<Product> original = Products.stream().filter(product -> product.getId() == id).findFirst();
        if(original.isPresent()) {
            Products.set(Products.indexOf(original.get()), new_product);
            return true;
        }
        else {
            return false;
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        //TODO Make a json backup for this
        //Removes product
        Optional<Product> original = Products.stream().filter(product -> product.getId() == id).findFirst();
        if(original.isPresent()) {
            Products.remove(original.get());
            return true;
        }
        else {
            return false;
        }
    }
}
