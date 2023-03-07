package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.ItemPostDto;
import sudols.ecopercent.service.ItemService;

import java.util.List;

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
        return itemService.findItemList(userId, category);
    }

    @GetMapping("/items/{itemid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Item GetItemDetail(@PathVariable("itemid") Long itemId) {
        System.out.println(itemId);
        return null;
    }

    @PatchMapping("/items/{itemid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateTumblerDetail(@PathVariable("itemid") Long itemId,
                                    @RequestBody() Object body) {
        System.out.println(itemId);
        System.out.println(body);
    }

    @DeleteMapping("/items/{itemid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteEcobag(@PathVariable("itemid") Long itemId) {
        System.out.println(itemId);
    }
}
