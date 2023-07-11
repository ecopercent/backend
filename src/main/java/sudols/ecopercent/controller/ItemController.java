package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.item.UpdateItemRequest;
import sudols.ecopercent.security.provider.JwtTokenProvider;
import sudols.ecopercent.service.ItemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/items")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemResponse createItem(HttpServletRequest request,
                                   @RequestPart("itemData") CreateItemRequest createItemRequest,
                                   @RequestPart(value = "itemImage", required = false) MultipartFile itemImageMultipartFile) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.createItem(email, createItemRequest, itemImageMultipartFile);
    }

    @GetMapping("/items")
    public List<ItemResponse> getMyItemListByCategory(HttpServletRequest request,
                                                      @RequestParam(value = "category") String category) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.getMyItemListByCategory(email, category);
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
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.updateItem(email, itemId, updateItemRequest, itemImageMultipartFile);
    }

    @PatchMapping("/items/{itemId}/usage-count")
    public ItemResponse increaseUsageCount(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.increaseUsageCount(email, itemId);
    }

    @PatchMapping("/items/{itemId}/title-tumbler")
    public ItemResponse changeTitleTumbler(HttpServletRequest request,
                                           @PathVariable("itemId") Long itemId) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.changeTitleTumbler(email, itemId);
    }

    @PatchMapping("/items/{itemId}/title-ecobag")
    public ItemResponse changeTitleEcobag(HttpServletRequest request,
                                          @PathVariable("itemId") Long itemId) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.changeTitleEcobag(email, itemId);
    }

    @GetMapping("/users/me/title-tumbler")
    public ItemResponse getTitleTumbler(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.getTitleTumbler(email);
    }

    @GetMapping("/users/me/title-ecobag")
    public ItemResponse getTitleEcobag(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return itemService.getTitleEcobag(email);
    }

    @DeleteMapping("/items/{itemId}")
    public void deleteItem(HttpServletRequest request,
                           @PathVariable("itemId") Long itemId) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        itemService.deleteItem(email, itemId);
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
