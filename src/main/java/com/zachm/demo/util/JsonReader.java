package com.zachm.demo.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.zachm.demo.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class JsonReader {

    public static List<Person> people = new ArrayList<>();

    //Rough Version of Simple JSON, Can easily be improved.
    public static void readPeopleJson(){

        try {
            JSONParser parser = new JSONParser();
            FileReader file = new FileReader(ResourceUtils.getFile("classpath:people.json"));
            Object obj = parser.parse(file);

            if(obj instanceof JSONObject) {
                JSONObject json = (JSONObject) obj;
                getPeople(json);
            }
            if(obj instanceof JSONArray) {
                JSONArray json = (JSONArray) obj;
                json.forEach(o -> {
                    JSONObject jsonobj = (JSONObject) o;
                    getPeople(jsonobj);
                });
            }
        }
        catch(ParseException|IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getPeople(JSONObject jsonobj) {
        String LastName;
        String Gender;
        long Age;

        if(!jsonobj.containsKey("lastName")) {
            LastName = "Unknown";
        }
        else {
            LastName = (String) jsonobj.get("lastName");
        }

        if(!jsonobj.containsKey("gender")) {
            Gender = "Unknown";
        }
        else {
            Gender = (String) jsonobj.get("gender");
        }

        if(!jsonobj.containsKey("age")) {
            Age = 0;
        }
        else {
            Age = (long) jsonobj.get("age");
        }

        Person person = new Person((String)jsonobj.get("firstName"),LastName,Age,Gender);
        people.add(person);
    }
}
