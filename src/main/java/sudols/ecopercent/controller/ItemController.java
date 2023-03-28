package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.service.ItemService;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public Item CreateItem(@Valid @RequestBody() CreateItemRequest itemData) {
        return itemService.createItem(itemData);
    }

    @GetMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<Item> GetItemList(@RequestParam("userId") Long userId,
                                  @RequestParam(value = "category", required = false) String category) {
        return itemService.getItemList(userId, category);
    }

    @GetMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetItem(@PathVariable("itemId") Long itemId) {
        return itemService.getItem(itemId);
    }

    @PatchMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> UpdateItem(@PathVariable("itemId") Long itemId,
                                     @RequestBody() UpdateItemRequest newItemData) {
        return itemService.updateItem(itemId, newItemData);
    }

    // TODO: up 이라는 건 RestAPI 스럽지 않은 이름인듯?
    @PatchMapping("/items/{itemId}/up")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> IncreaseUsageCount(@PathVariable("itemId") Long itemId) {
        return itemService.increaseUsageCount(itemId);
    }

    @PatchMapping("/users/{userId}/items/{itemId}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> UpdateTitleTumbler(@PathVariable("itemId") Long itemId,
                                             @PathVariable("userId") Long userId) {
        return itemService.updateTitleTumbler(itemId, userId);
    }

    @PatchMapping("/users/{userId}/items/{itemId}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> UpdateTitleEcobag(@PathVariable("itemId") Long itemId,
                                            @PathVariable("userId") Long userId) {
        return itemService.updateTitleEcobag(itemId, userId);
    }

    @GetMapping("/users/{userId}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetTitleTumbler(@PathVariable("userId") Long userId) {
        return itemService.getTitleTumbler(userId);
    }

    @GetMapping("/users/{userId}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetTitleEcobag(@PathVariable("userId") Long userId) {
        return itemService.getTitleEcobag(userId);
    }

    @DeleteMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
    }

    // TEST API
    @GetMapping("/items/all")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<Item> GetAllItemList() {
        return itemService.getAllItemList();
    }

    // TEST API
    @DeleteMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteAllItem() {
        itemService.deleteAllItem();
    }
}
