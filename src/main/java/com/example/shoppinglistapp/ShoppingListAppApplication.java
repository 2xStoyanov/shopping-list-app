package com.example.shoppinglistapp;

import com.example.shoppinglistapp.Item.*;
import com.datastax.oss.driver.api.core.CqlSession;
import com.example.shoppinglistapp.connection.DataStaxAstraProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Path;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class ShoppingListAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingListAppApplication.class, args);
	}


/**
	@PostConstruct
	public void start(){
		//Item item = new Item(new ItemPrimaryKey(UUID.randomUUID(),  "In progress", "High"), "orange", 1, "food");
		try {
			//itemService.createItem("peach", 1, "food", "In progress", "High");

			//ItemPrimaryKey itemPrimaryKey = itemService.getAllItems().stream().limit(1).map(Item::getId).findFirst().get();
			//itemService.updateItem(itemPrimaryKey.getId(), null, null, null);
			//item = itemService.getItemByIdId(itemPrimaryKey.getId()).orElse(null);
			//itemService.updatePriority(item.getId().getId(), "Medium");
			//System.out.println(item.getId().getId());


		} catch (Exception e) {
			System.out.println(e);
		}

		//itemService.getAllItems().forEach(item -> System.out.println(item.toString()));
		try {
			/** //Create new item entry and put it in the table
			Item item = new Item(UUID.randomUUID(), "apple", 1, "food", "High", "In progress");
			itemService.createItem(item);

			//get the id of the first entry and get the item by its id
			UUID uuid = itemService.getAllItems().stream().limit(1).map(Item::getId).findFirst().get();
			item = itemService.getItemById(uuid).orElse(null);
			System.out.println(item.toString());

			//update table entry
			item.setPriority("Low");
			itemService.updateItem(item);
			uuid = itemService.getAllItems().stream().limit(1).map(Item::getId).findFirst().get();
			item = itemService.getItemById(uuid).orElse(null);
			System.out.println(item.toString());

			//delete the entry
			itemService.deleteItem(item.getId());
			itemService.getAllItems().forEach(i -> System.out.println(i.toString()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/


	@Bean
	public CqlSession sessionBuilder(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return CqlSession.builder()
				.withCloudSecureConnectBundle(bundle)
				.withAuthCredentials("sfXdUYCDbxODHuZNOCZefxiO","OJZIQ+q.rJUL8OJaqp7q2IYyUQ+-c8OZJQ09NvP7GHPnagmEDafsoUfcrEbNmMKc021Oj1jbH0anBgRZZQN0dLwJZcFhie8qoIkdp8es5v,k47j88edNcYOhoMh5X3Ry")
				.withKeyspace("demo_projects")
				.build();
	}



}
