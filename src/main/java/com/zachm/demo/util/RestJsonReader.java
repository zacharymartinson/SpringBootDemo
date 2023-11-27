package com.zachm.demo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachm.demo.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestJsonReader {

    public static List<Product> products = new ArrayList<>();

    //I do want to make it so it can ignore missing data, should be really easy
    public static void readRestJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(new URL("https://dummyjson.com/products")).get("products");

            node.forEach(jsonNode -> {
                //can cut a line here with jsonNode.toString();
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

