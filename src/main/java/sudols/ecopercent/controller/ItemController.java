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
    public ItemResponse UpdateItem(HttpServletRequest request,
                                   @PathVariable("itemId") Long itemId,
                                   @RequestBody() UpdateItemRequest updateItemRequest) {
        return itemService.updateItem(request, itemId, updateItemRequest);
    }

    @PatchMapping("/items/{itemId}/usage-count")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse IncreaseUsageCount(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        return itemService.increaseUsageCount(request, itemId);
    }

    @PatchMapping("/items/{itemId}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse ChangeTitleTumbler(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        return itemService.changeTitleTumbler(request, itemId);
    }

    @PatchMapping("/items/{itemId}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse ChangeTitleEcobag(HttpServletRequest request,
                                          @PathVariable("itemId") Long itemId) {
        return itemService.changeTitleEcobag(request, itemId);
    }

    @GetMapping("/users/me/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse GetTitleTumbler(HttpServletRequest request) {
        return itemService.getTitleTumbler(request);
    }

    @GetMapping("/users/me/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public ItemResponse GetTitleEcobag(HttpServletRequest request) {
        return itemService.getTitleEcobag(request);
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
