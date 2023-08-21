package com.example.shoppinglistapp.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/list")
    public String showShoppingList(Model model) {
        List<Item> shoppingList = itemService.getAllItems();
        model.addAttribute("shoppingList", shoppingList);
        return "shopping-list";
    }

    @GetMapping("/{uuid}")
    public String getItemDetails(@PathVariable UUID uuid, Model model) {
        Optional<Item> optionalItem = itemService.getItemByIdId(uuid);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            model.addAttribute("item", item);
            return "item-details";
        } else {
            // Handle item not found
            return "error";
        }
    }

    @GetMapping("/{uuid}/delete")
    public String deleteItem(@PathVariable UUID uuid) {
        Optional<Item> optionalItem = itemService.getItemByIdId(uuid);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            itemService.deleteItem(item.getId());
            return "redirect:/items/list";
        } else {
            // Handle item not found
            return "error";
        }
    }

    @GetMapping("/{uuid}/edit")
    public String showEditForm(@PathVariable UUID uuid, Model model) {
        Optional<Item> optionalItem = itemService.getItemByIdId(uuid);

        if (optionalItem.isPresent()) {
            model.addAttribute("item", optionalItem.get());
            return "edit-item";
        } else {
            // Handle item not found
            return "error";
        }
    }

    @PostMapping("/edit")
    public String editItem(@ModelAttribute Item item) {
        itemService.updateItem(item);
        return "redirect:/items/list";
    }

    @GetMapping("/create")
    public String showAddItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "add-item";
    }

    @PostMapping("/create")
    public String createItem(@ModelAttribute Item item) {
        itemService.createItem(item);
        return "redirect:/items/list";
    }


}
