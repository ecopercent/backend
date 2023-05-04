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

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemResponse createItem(HttpServletRequest request,
                                   @RequestPart("itemData") CreateItemRequest createItemRequest,
                                   @RequestPart(value = "itemImage", required = false) MultipartFile itemImageMultipartFile) {
        return itemService.createItem(request, createItemRequest, itemImageMultipartFile);
    }

    @GetMapping("/items")
    @ResponseBody
    public List<ItemResponse> getMyItemListByCategory(HttpServletRequest request,
                                                      @RequestParam(value = "category") String category) {
        return itemService.getMyItemListByCategory(request, category);
    }

    @GetMapping("/items/{itemId}")
    @ResponseBody
    public ItemResponse getItem(@PathVariable("itemId") Long itemId) {
        return itemService.getItem(itemId);
    }

    @PatchMapping("/items/{itemId}")
    @ResponseBody
    public ItemResponse updateItem(HttpServletRequest request,
                                   @PathVariable("itemId") Long itemId,
                                   @RequestPart("itemData") UpdateItemRequest updateItemRequest,
                                   @RequestPart(value = "itemImage", required = false) MultipartFile itemImageMultipartFile) {
        return itemService.updateItem(request, itemId, updateItemRequest, itemImageMultipartFile);
    }

    @PatchMapping("/items/{itemId}/usage-count")
    @ResponseBody
    public ItemResponse increaseUsageCount(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        return itemService.increaseUsageCount(request, itemId);
    }

    @PatchMapping("/items/{itemId}/title-tumbler")
    @ResponseBody
    public ItemResponse changeTitleTumbler(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        return itemService.changeTitleTumbler(request, itemId);
    }

    @PatchMapping("/items/{itemId}/title-ecobag")
    @ResponseBody
    public ItemResponse changeTitleEcobag(HttpServletRequest request,
                                          @PathVariable("itemId") Long itemId) {
        return itemService.changeTitleEcobag(request, itemId);
    }

    @GetMapping("/users/me/title-tumbler")
    @ResponseBody
    public ItemResponse getTitleTumbler(HttpServletRequest request) {
        return itemService.getTitleTumbler(request);
    }

    @GetMapping("/users/me/title-ecobag")
    @ResponseBody
    public ItemResponse getTitleEcobag(HttpServletRequest request) {
        return itemService.getTitleEcobag(request);
    }

    @DeleteMapping("/items/{itemId}")
    @ResponseBody
    public void deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
    }

    // TEST API
    @GetMapping("/items/all")
    @ResponseBody
    public List<ItemResponse> getAllItemList() {
        return itemService.getAllItemList();
    }

    // TEST API
    @DeleteMapping("/items")
    @ResponseBody
    public void deleteAllItem() {
        itemService.deleteAllItem();
    }
}
