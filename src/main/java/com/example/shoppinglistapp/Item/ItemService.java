package com.example.shoppinglistapp.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    private KafkaTemplate<String, String> kafkaTemplate;

    public ItemService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(ItemPrimaryKey itemPrimaryKey) {
        return itemRepository.findById(itemPrimaryKey);
    }

    public Optional<Item> getItemByIdId(UUID uuid) {
        return itemRepository.findByIdId(uuid);
    }

    public void createItem(Item item) {
        // Generate a new UUID
        UUID id = UUID.randomUUID();
        item.setId(new ItemPrimaryKey(id, item.getId().getStatus(), item.getId().getPriority()));

        itemRepository.save(item);
    }

    public Item updateItem(Item updatedItem) {
        Optional<Item> optionalItem = itemRepository.findByIdId(updatedItem.getId().getId());

        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();

            // Update the fields if they are not null
            if (updatedItem.getItemName() != null) {
                existingItem.setItemName(updatedItem.getItemName());
            }
            if (updatedItem.getQuantity() != null) {
                existingItem.setQuantity(updatedItem.getQuantity());
            }
            if (updatedItem.getCategory() != null) {
                existingItem.setCategory(updatedItem.getCategory());
            }

            // Check for changes in status or priority
            if (!existingItem.getId().getStatus().equals(updatedItem.getId().getStatus())
                    || !existingItem.getId().getPriority().equals(updatedItem.getId().getPriority())) {
                // Delete the existing item
                itemRepository.delete(existingItem);

                // Save the updated item
                return itemRepository.save(updatedItem);
            } else {
                // Save the changes to the existing item
                return itemRepository.save(existingItem);
            }
        }

        return null; // Item not found
    }

    public void deleteItem(ItemPrimaryKey itemPrimaryKey) {
        itemRepository.deleteById(itemPrimaryKey);
    }

    public void saveItem(Item item) {
        // Save the item to Cassandra
        itemRepository.save(item);
    }

    // Scheduled Items can be added automatically (e.g. once weekly)
    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void addAutomaticItems() {
        // Get scheduled Items (e.g. read them from a database table)
        Item newItem = new Item(new ItemPrimaryKey(UUID.randomUUID(), "In progress", "Medium"), "Milk", 1, "groceries");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert the Item object to a JSON string
            String jsonString = objectMapper.writeValueAsString(newItem);

            // Sent the item to Kafka
            kafkaTemplate.send("add-new-item", jsonString);
            System.out.println("Automatic item sent to Kafka.");
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }


}
