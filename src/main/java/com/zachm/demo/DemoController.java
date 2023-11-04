package com.zachm.demo;

import com.zachm.demo.util.JsonReader;
import com.zachm.demo.util.RestJsonReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DemoController {

    @RequestMapping("/people")
    public List<Products> getPeople() {

        // -------------------- Manually Add ------------------------
        //List<Person> list = new ArrayList<>();
        //list.add(new Person("Zachary", "Martinson", 26, "Male"));
        //list.add(new Person("Billy", "Bob", 66, "Male"));

        // -------------------- From Local File ------------------------
        //return JsonReader.people;

        // -------------------- From Rest API ------------------------
        return RestJsonReader.products;
    }
}
