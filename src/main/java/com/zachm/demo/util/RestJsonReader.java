package com.zachm.demo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachm.demo.DemoApplication;
import com.zachm.demo.DemoController;
import com.zachm.demo.Product;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * I use an example JSON to make it easier.
 * This is where I connect to this demo JSON
 */
public class RestJsonReader {

    public static List<Product> products = new ArrayList<>();
    //Standard Jackson Json Reader.
    public static void readRestJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(new URL("https://dummyjson.com/products")).get("products");

            node.forEach(jsonNode -> {
                //can cut a line here with jsonNode.toString(); But I found readability easier with parser.
                JsonParser parser = jsonNode.traverse();
                try {
                    Product product = mapper.readValue(parser, Product.class);
                    products.add(product);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

