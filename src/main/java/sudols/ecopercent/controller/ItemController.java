package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.ItemPatchDetailDto;
import sudols.ecopercent.dto.item.ItemPostDto;
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
    public Long AddItem(@Valid @RequestBody() ItemPostDto itemData) {
        return itemService.add(itemData);
    }

    @GetMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<Item> GetItemList(
            @RequestParam("userid") Long userId,
            @RequestParam(value = "category", required = false) String category
    ) {
        return itemService.findListByCategory(userId, category);
    }

    @GetMapping("/items/{itemid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetItemDetail(@PathVariable("itemid") Long itemId) {
        return itemService.findOne(itemId);
    }

    @PatchMapping("/items/{itemid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateItemDetail(
            @PathVariable("itemid") Long itemId,
            @RequestBody() ItemPatchDetailDto newItemData
    ) {
        itemService.updateDetail(itemId, newItemData);
    }

    @PatchMapping("/items/{itemid}/up")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Long IncreaseItemUsageCount(@PathVariable("itemid") Long itemId) {
        return itemService.increaseUsageCount(itemId);
    }

    @DeleteMapping("/items/{itemid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteOneItem(@PathVariable("itemid") Long itemId) {
        itemService.deleteOne(itemId);
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
