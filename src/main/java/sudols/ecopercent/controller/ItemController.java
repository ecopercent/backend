package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemResponse createItem(HttpServletRequest request,
                                   @RequestPart("itemData") CreateItemRequest createItemRequest,
                                   @RequestPart(value = "itemImage", required = false) MultipartFile itemImageMultipartFile) {
        return itemService.createItem(request, createItemRequest, itemImageMultipartFile);
    }

    @GetMapping("/items")
    public List<ItemResponse> getMyItemListByCategory(HttpServletRequest request,
                                                      @RequestParam(value = "category") String category) {
        return itemService.getMyItemListByCategory(request, category);
    }

    @GetMapping("/items/{itemId}")
    public ItemResponse getItem(@PathVariable("itemId") Long itemId) {
        return itemService.getItem(itemId);
    }

    @PatchMapping("/items/{itemId}")
    public ItemResponse updateItem(HttpServletRequest request,
                                   @PathVariable("itemId") Long itemId,
                                   @RequestPart(value = "itemData", required = false) UpdateItemRequest updateItemRequest,
                                   @RequestPart(value = "itemImage", required = false) MultipartFile itemImageMultipartFile) {
        return itemService.updateItem(request, itemId, updateItemRequest, itemImageMultipartFile);
    }

    @PatchMapping("/items/{itemId}/usage-count")
    public ItemResponse increaseUsageCount(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        return itemService.increaseUsageCount(request, itemId);
    }

    @PatchMapping("/items/{itemId}/title-tumbler")
    public ItemResponse changeTitleTumbler(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        return itemService.changeTitleTumbler(request, itemId);
    }

    @PatchMapping("/items/{itemId}/title-ecobag")
    public ItemResponse changeTitleEcobag(HttpServletRequest request,
                                          @PathVariable("itemId") Long itemId) {
        return itemService.changeTitleEcobag(request, itemId);
    }

    @GetMapping("/users/me/title-tumbler")
    public ItemResponse getTitleTumbler(HttpServletRequest request) {
        return itemService.getTitleTumbler(request);
    }

    @GetMapping("/users/me/title-ecobag")
    public ItemResponse getTitleEcobag(HttpServletRequest request) {
        return itemService.getTitleEcobag(request);
    }

    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
    }

    // TEST API
    @GetMapping("/items/all")
    public List<ItemResponse> getAllItemList() {
        return itemService.getAllItemList();
    }

    // TEST API
    @DeleteMapping("/items")
    public void deleteAllItem() {
        itemService.deleteAllItem();
    }
}
