package com.zachm.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachm.demo.util.RestJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;


/**
 * Standard Controller Class that I created for Demonstration
 * This would be the product controller, ideally you would have multiple controllers
 *
 * http://localhost:8081/products
 * The apiKey is 12345ABC
 * An example would be http://localhost:8081/products?id=1&apiKey=12345ABC\
 */
@RestController
@RequestMapping("/products")
public class DemoController {
    public static List<Product> Products = RestJsonReader.products;

    /**
     * Gets the products, we can filter by id here.
     */
    @GetMapping
    public Object getProducts(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "brand", required = false) String brand,
                              @RequestParam(value = "apiKey") String apiKey) {

        //THIS IS FOR DEMO AND IS JUST QUICK
        //IDEALLY YOU WOULD SEND THIS TO A DATABASE TO ADD 1 TO USAGE AND TO CHECK IF THE KEY IS IN THE DATABASE
        if(!apiKey.equals("12345ABC")) {
            return "INVALID KEY";
        }
        else {

            //WE CANT USE BOTH BRAND AND ID
            if(id != null && brand != null) {
                return "CANT USE BRAND AND ID";
            }

            //FILTER BY ID
            if(id != null) {
                if(id == 0) {
                    return Products.get(id);
                }
                else {
                    return Products.get(id-1);
                }
            }

            //FILTER BY BRAND
            if(brand != null) {
                List<Product> branded = new ArrayList<>();

                Products.forEach(product -> {
                    if(product.getBrand().equals(brand)) {
                        branded.add(product);
                    }
                });

                return branded;
            }
            return Products;

        }
    }
    /**
     * Adds a new product
     */
    @PostMapping
    public boolean addProduct(@RequestBody Product new_product) {
        //TODO Make a json backup for this. Filter by Title
        if(Products.contains(new_product)) {
            return false;
        }
        else {
            //Keeping ID in check
            new_product.setId(Products.size()+1);
            Products.add(new_product);
            return true;
        }
    }
    /**
     * Updates a product
     * I filter through a list using the product ID, doesn't have to be ID, could be a name for example
     * I avoid using null here for scalability
     */
    @PutMapping("/{id}")
    public boolean updateProduct(@RequestBody Product new_product, @PathVariable Long id) {
        //TODO UPDATE FOR KEY
        Optional<Product> original = Products.stream().filter(product -> product.getId() == id).findFirst();
        if(original.isPresent()) {
            Products.set(Products.indexOf(original.get()), new_product);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Removes a product from the list
     */
    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        //TODO UPDATE FOR KEY
        Optional<Product> original = Products.stream().filter(product -> product.getId() == id).findFirst();
        if(original.isPresent()) {
            Products.remove(original.get());
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Changes a couple of things to a product
     *
     * I use reflection here, just for show-off but in a professional setting I HIGHLY RECOMMEND ReflectionUtils.
     */
    @PatchMapping("/{id}")
    public boolean partialUpdateProduct(@RequestBody Map<String, Object> map, @PathVariable Long id) {
        Optional<Product> original = Products.stream().filter(product -> product.getId() == id).findFirst();
        ObjectMapper mapper = new ObjectMapper();

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
                        if(field.getName().equals(s) && !field.getName().equals("id")) {
                            //set the new value to the original
                            field.set(original.get(), o);
                        }
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            return true;
        }
        else {
            return false;
        }
    }
}
