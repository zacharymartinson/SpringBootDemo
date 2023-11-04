package com.zachm.demo;

import com.zachm.demo.util.JsonReader;
import com.zachm.demo.util.RestJsonReader;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	//http://localhost:8081/people

	public static void main(String[] args){
		SpringApplication.run(DemoApplication.class, args);
		JsonReader.readPeopleJson();
		RestJsonReader.readRestJSON();
	}



}
