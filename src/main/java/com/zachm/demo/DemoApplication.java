package com.zachm.demo;

import com.zachm.demo.util.RestJsonReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	//http://localhost:8081/products

	public static void main(String[] args){
		if(DemoController.products.isEmpty()) {
			//This is for demo purposes.
			RestJsonReader.readRestJSON();
		}
		SpringApplication.run(DemoApplication.class, args);
	}



}
