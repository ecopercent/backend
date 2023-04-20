package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.service.ItemService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemResponse CreateItem(HttpServletRequest request, @RequestBody() CreateItemRequest createItemRequest) {
        return itemService.createItem(request, createItemRequest);
    }

    @GetMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<ItemResponse> GetItemList(HttpServletRequest request,
                                          @RequestParam(value = "category") String category) {
        return itemService.getItemListByCategory(request, category);
    }

    @GetMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse GetItem(@PathVariable("itemId") Long itemId) {
        return itemService.getItem(itemId);
    }

    @PatchMapping("/items/{itemId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse UpdateItem(@PathVariable("itemId") Long itemId,
                                             @RequestBody() UpdateItemRequest updateItemRequest) {
        return itemService.updateItem(itemId, updateItemRequest);
    }

    // TODO: up 이라는 건 RestAPI 스럽지 않은 이름인듯?
    @PatchMapping("/items/{itemId}/up")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<ItemResponse> IncreaseUsageCount(@PathVariable("itemId") Long itemId) {
        return itemService.increaseUsageCount(itemId);
    }

    @PatchMapping("/users/{userId}/items/{itemId}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<ItemResponse> UpdateTitleTumbler(@PathVariable("itemId") Long itemId,
                                                     @PathVariable("userId") Long userId) {
        return itemService.updateTitleTumbler(itemId, userId);
    }

    @PatchMapping("/users/{userId}/items/{itemId}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<ItemResponse> UpdateTitleEcobag(@PathVariable("itemId") Long itemId,
                                                    @PathVariable("userId") Long userId) {
        return itemService.updateTitleEcobag(itemId, userId);
    }

    @GetMapping("/users/{userId}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<ItemResponse> GetTitleTumbler(@PathVariable("userId") Long userId) {
        return itemService.getTitleTumbler(userId);
    }

    @GetMapping("/users/{userId}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<ItemResponse> GetTitleEcobag(@PathVariable("userId") Long userId) {
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
    public List<ItemResponse> GetAllItemList() {
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
