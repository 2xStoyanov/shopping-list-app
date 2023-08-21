package com.example.shoppinglistapp.Kafka;

import com.example.shoppinglistapp.Item.Item;
import com.example.shoppinglistapp.Item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaListeners {

    @Autowired
    ItemRepository itemRepository;

    @KafkaListener(topics = "add-new-item", groupId = "groupId")
    void listener(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            // Convert JSON string to Item object
            Item item = objectMapper.readValue(jsonString, Item.class);

            // Save the new Item
            itemRepository.save(item);

            // Print a message
            System.out.println("Item " + item.getItemName() + " added to the list.");


        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }
}
