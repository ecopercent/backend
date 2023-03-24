package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.RequestPatchItemDetailDto;
import sudols.ecopercent.dto.item.RequestPostItemDto;
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
    public Long AddItem(@Valid @RequestBody() RequestPostItemDto itemData) {
        return itemService.addItem(itemData);
    }

    @GetMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<Item> GetItemList(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "category", required = false) String category
    ) {
        return itemService.findListByCategory(userId, category);
    }

    @GetMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetItemDetail(@PathVariable("itemId") Long itemId) {
        return itemService.findOne(itemId);
    }

    @PatchMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateItemDetail(
            @PathVariable("itemId") Long itemId,
            @RequestBody() RequestPatchItemDetailDto newItemData
    ) {
        itemService.updateDetail(itemId, newItemData);
    }

    @PatchMapping("/items/{itemId}/up")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Long IncreaseItemUsageCount(@PathVariable("itemId") Long itemId) {
        return itemService.increaseUsageCount(itemId);
    }

    @DeleteMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteOneItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteOne(itemId);
    }

    @PatchMapping("/users/{userId}/items/{itemId}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateTitleTumbler(@PathVariable("itemId") Long itemId,
                                   @PathVariable("userId") Long userId) {
        itemService.updateTitleTumbler(userId, itemId);
    }

    @PatchMapping("/users/{userId}/items/{itemId}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateTitleEcobag(@PathVariable("itemId") Long itemId,
                                  @PathVariable("userId") Long userId) {
        itemService.updateTitleEcobag(userId, itemId);
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

    // TEST API
    @GetMapping("/items/all")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<Item> GetAllItemList() {
        return itemService.findAll();
    }

    // TEST API
    @DeleteMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteAllItem() {
        itemService.deleteAll();
    }
}
