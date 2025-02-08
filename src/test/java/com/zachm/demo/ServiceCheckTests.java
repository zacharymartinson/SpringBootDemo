package com.zachm.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ServiceCheckTests {

    private final DemoService service = new DemoService();

    /**
     * Tests for VALID Keys
     */
    @Test
    void testValidKeyCheck() {
        assertTrue(service.isValidKey("12345ABC"));
    }

    @Test
    void testValidSearchCheck() {
        //TODO Likely to be removed as this needs to be checked on everything.
        assertFalse(service.isValidCheck(1, "Nike"));
        assertTrue(service.isValidCheck(1, null));
        assertTrue(service.isValidCheck(null, "Nike"));
    }
}
