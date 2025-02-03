package com.zachm.demo;

import com.zachm.demo.util.RestJsonReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DemoService {

    private final List<Product> products;

    public DemoService() {
        this.products = RestJsonReader.products;
    }

    public boolean isValidKey(String key) {
        return key.equals("12345ABC");
    }

    public boolean isValidCheck(Integer id, String brand) {
        return id == null || brand == null;
    }

    public List<Product> getProducts(Integer id, String brand, String tag, String sku) {
        if(id != null) { return caseID(id); }
        if(brand != null) {  return caseBrand(brand); }
        if(tag != null) { return caseTag(tag); }
        if(sku != null) { return caseSKU(sku); }

        return products;
    }

    private List<Product> caseID(Integer id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .collect(Collectors.toList());
    }

    private List<Product> caseBrand(String brand) {
        return products.stream()
                .filter(product -> product.getBrand().contains(brand))
                .collect(Collectors.toList());
    }

    private List<Product> caseTag(String tag) {
        return products.stream()
                .filter(product -> product.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    private List<Product> caseSKU(String sku) {
        //Edge case for non digit scenarios.
        return products.stream()
                .filter(product -> product.getSku().chars().allMatch(Character::isDigit) && product.getSku().equals(sku))
                .collect(Collectors.toList());
    }


}
