package com.zachm.demo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachm.demo.Person;
import com.zachm.demo.Products;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestJsonReader {

    public static List<Products> products = new ArrayList<>();

    //This was made in 2-3hrs from zero experience. And now I can probably do this in 5 minutes. This is really good code in my opion.
    //I do want to make it so it can ignore missing data, should be really easy
    public static void readRestJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(new URL("https://dummyjson.com/products")).get("products");

            node.forEach(jsonNode -> {
                JsonParser parser = jsonNode.traverse();
                try {
                    Products product = mapper.readValue(parser, Products.class);
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

