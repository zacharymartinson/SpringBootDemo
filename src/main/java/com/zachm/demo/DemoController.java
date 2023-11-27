package com.zachm.demo;

import com.zachm.demo.util.RestJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        //TODO Make a json backup for this. Filter by Title
        //Adds a new product.
        if(Products.contains(new_product)) {
            return false;
        }
        else {
            new_product.setId(Products.size()+1);
            Products.add(new_product);
            return true;
        }
    }
    @PutMapping("/{id}")
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

    @PatchMapping("/{id}")
    public boolean partialUpdateProduct(@RequestBody Map<String, Object> map, @PathVariable Long id) {
        //Changes a value in a specific Product
        //I use reflection here, basically just getting all the fields in a class and filtering them
        //I do this instead of writing a for each where I do an if statement for every item
        //This is a bit complicated unless you've done it before. Or use ReflectionUtil which I found out after.
        Optional<Product> original = Products.stream().filter(product -> product.getId() == id).findFirst();

        //Get the fields
        Field[] fields = Product.class.getDeclaredFields();

        if(original.isPresent()) {

            //We have to go through everything in the map
            map.forEach((s, o) -> {

                //The same for all the fields in our products class
                for(Field field : fields) {

                    //We have to allow us to access them
                    field.setAccessible(true);

                    //Surround with try and catch
                    try {
                        //I use the names to match them.
                        //!!!ITS IMPORTANT TO HAVE MATCHING NAMES OR FILTER THEM TO MATCH!!!
                        if(field.getName() == s && s != "id") {
                            //set the new value to the original
                            field.set(original.get(), o);
                        }
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            //Saves the product
            Products.set(Products.indexOf(original.get()),original.get());
            return true;
        }
        else {
            return false;
        }
    }
}
