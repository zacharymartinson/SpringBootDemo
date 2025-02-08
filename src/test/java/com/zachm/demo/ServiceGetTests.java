package com.zachm.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ServiceGetTests {

    private final DemoService service = new DemoService();

    /**
     * Generates a List with 1 product in it for testing purposes.
     */
    @BeforeEach
    void generateProducts() {
        service.products = List.of(
                new Product(1,
                        "Nike Shoes",
                        "Running shoes",
                        99.99,
                        10.0,
                        4.5,
                        50,
                        "Nike",
                        "Footwear",
                        "nike.jpg",
                        List.of("nike1.jpg", "nike2.jpg"),
                        List.of("sports", "shoes"),
                        "NIK12345",
                        1.2,
                        new Product.Dimensions(10,5,3),
                        List.of(new Product.Review(5, "Great Shoes!", new Date(), "John Smith", "JSmith33@gmail.com")),
                        "2 years",
                        "Ships in 2 days",
                        "In Stock",
                        "30-day return",
                        1, new Product.Meta())
        );
    }

    /**
     * Tests GET for ID
     */
    @Test
    void testProductGetID() {
        List<Product> result = service.getProducts(1, null, null, null);
        assertEquals(1, result.get(0).getId());
    }

    /**
     * Tests GET for BRAND
     */
    @Test
    void testProductGetBrand() {
        List<Product> result = service.getProducts(null, "Nike", null, null);
        assertEquals("Nike", result.get(0).getBrand());
    }

    /**
     * Tests GET for TAG
     */
    @Test
    void testProductGetTag() {
        List<Product> result = service.getProducts(null, null, "shoes", null);
        assertTrue(result.get(0).getTags().contains("shoes"));
    }

    /**
     * Tests GET for SKU
     */
    @Test
    void testProductGetSku() {
        List<Product> result = service.getProducts(null, null, null, "NIK12345");
        assertEquals("NIK12345", result.get(0).getSku());
    }
}
