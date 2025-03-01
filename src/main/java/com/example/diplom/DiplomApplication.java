package com.example.diplom;

import com.example.diplom.model.Dish;
import com.example.diplom.model.Pizza;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiplomApplication {

	public static void main(String[] args) {

		Dish dish=new Dish("Pizza", "any pizza", 10.0);
		System.out.println(dish);

		Pizza pizza=new Pizza("Amore", "like pizza", 12.0);

		System.out.println(pizza);


//		SpringApplication.run(DiplomApplication.class, args);
	}

}
