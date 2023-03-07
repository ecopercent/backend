package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.ItemPostDto;
import sudols.ecopercent.service.ItemService;

import java.util.ArrayList;
import java.util.Date;
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
    public List<Item> GetEcobagListByUserId(
            @RequestParam("userid") Long userId,
            @RequestParam("category") String category
    ) {
        ArrayList<Item> list = new ArrayList<Item>();
        Date currentDate = new Date();

        Item ecobag1 = Item.builder()
                .id(0L)
                .image("임시 이미지?")
                .nickname("tumbler1")
                .brand("brand1")
                .price(42)
                .usageCount(0L)
                .purchaseDate(null)
                .registrationDate(currentDate)
                .latestDate(null)
                .build();

        Item ecobag2 = Item.builder()
                .id(0L)
                .image("임시 이미지?")
                .nickname("tumbler2")
                .brand("brand2")
                .price(42)
                .usageCount(0L)
                .purchaseDate(null)
                .registrationDate(currentDate)
                .latestDate(null)
                .build();
        list.add(ecobag1);
        list.add(ecobag2);
        return list;
    }

    @GetMapping("/items/tumblers/{tumblerid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Item GetTumblerDetail(@PathVariable("tumblerid") Long tumblerId) {
        System.out.println(tumblerId);
        return null;
    }

    @GetMapping("/items/ecobags/{ecobagid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Item GetEcobagDetail(@PathVariable("ecobagid") Long ecobagId) {
        System.out.println(ecobagId);
        return null;
    }

    @PutMapping("/items/tumblers/{tumblerid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateTumblerDetail(@PathVariable("tumblerid") Long tumblerId,
                                    @RequestBody() Object body) {
        System.out.println(tumblerId);
        System.out.println(body);
    }

    @PutMapping("/items/ecobags/{ecobagid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateEcobagDetail(@PathVariable("ecobagid") Long ecobagId,
                                   @RequestBody() Object body) {
        System.out.println(ecobagId);
        System.out.println(body);
    }

    @DeleteMapping("/items/tumblers/{tumblerid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteTumbler(@PathVariable("tumblerid") Long tumblerId) {
        System.out.println(tumblerId);
    }

    @DeleteMapping("/items/ecobags/{ecobagid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteEcobag(@PathVariable("ecobagid") Long ecobagId) {
        System.out.println(ecobagId);
    }
}
