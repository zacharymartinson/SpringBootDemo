package com.zachm.demo;

import com.zachm.demo.util.RestJsonReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;


/**
 * Standard Controller Class that I created for Demonstration
 * This would be the product controller, ideally you would have multiple controllers
 *
 * http://localhost:8081/products
 * The apiKey is 12345ABC
 * An example would be http://localhost:8081/products?id=1&apiKey=12345ABC
 */
@RestController
@RequestMapping("/products")
public class DemoController {

    public static final List<Product> products = RestJsonReader.products;
    private final DemoService service;

    public DemoController(DemoService service) {
        this.service = service;
    }

    /**
     * Gets the products, we can filter by id here.
     */
    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(value = "id", required = false) Integer id,
                                      @RequestParam(value = "apiKey") String apiKey,
                                      @RequestParam(value = "brand", required = false) String brand,
                                      @RequestParam(value = "tag", required = false) String tag,
                                      @RequestParam(value = "sku", required = false) String sku) {

        if(!service.isValidKey(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID KEY");
        }

        if(!service.isValidCheck(id, brand)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID REQUEST OR SYNTAX");
        }


        try {
            List<Product> filtered = service.getProducts(id,brand,tag,sku);
            return ResponseEntity.ok(filtered);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR" + e.getMessage());
        }

    }
    /**
     * Adds a new product
     */
    @PostMapping
    public boolean addProduct(@RequestBody Product new_product, @RequestParam(value = "apiKey") String apiKey) {
        //TODO Make a json backup for this. Filter by Title
        if(isBadKey(apiKey)) {
            return false;
        }
        if(products.contains(new_product)) {
            return false;
        }
        else {
            //Keeping ID in check
            new_product.setId(products.size()+1);
            products.add(new_product);
            return true;
        }
    }
    /**
     * Updates a product
     * I filter through a list using the product ID, doesn't have to be ID, could be a name for example
     * I avoid using null here for scalability
     */
    @PutMapping
    public boolean updateProduct(@RequestBody Product new_product, @RequestParam(value = "id") Long id, @RequestParam(value = "apiKey") String apiKey) {
        if(isBadKey(apiKey)) {
            return false;
        }
        Optional<Product> original = products.stream().filter(product -> product.getId() == id).findFirst();
        if(original.isPresent()) {
            products.set(products.indexOf(original.get()), new_product);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Removes a product from the list
     */
    @DeleteMapping
    public boolean deleteProduct(@RequestParam(value = "id") Long id, @RequestParam(value = "apiKey") String apiKey) {
        if(isBadKey(apiKey)) {
            return false;
        }
        Optional<Product> original = products.stream().filter(product -> product.getId() == id).findFirst();
        if(original.isPresent()) {
            products.remove(original.get());
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Changes a couple of things to a product
     *
     * I use reflection here, ReflectionUtils does the same thing.
     */
    @PatchMapping
    public boolean partialUpdateProduct(@RequestBody Map<String, Object> map, @RequestParam(value = "id") Long id, @RequestParam(value = "apiKey") String apiKey) {
        Optional<Product> original = products.stream().filter(product -> product.getId() == id).findFirst();

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

    private boolean isBadKey(String key) {
        return !key.equals("12345ABC");
    }
}
