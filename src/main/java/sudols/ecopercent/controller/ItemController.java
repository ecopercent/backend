package sudols.ecopercent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ItemController {

    @GetMapping("/items/tumblers")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<Item> GetTumblerListByUserId(@RequestParam("userid") Long userId) {
        ArrayList<Item> list = new ArrayList<Item>();
        Date currentDate = new Date();

        Item tumbler1 = Item.builder()
                .id(0L)
                .image("임시 이미지?")
                .nickname("tumbler1")
                .brand("brand1")
                .price(42)
                .usageCount(0L)
                .purchaseDate(null)
                .registrationDate(currentDate)
                .lastestDate(null)
                .build();

        Item tumbler2 = Item.builder()
                .id(0L)
                .image("임시 이미지?")
                .nickname("tumbler2")
                .brand("brand2")
                .price(42)
                .usageCount(0L)
                .purchaseDate(null)
                .registrationDate(currentDate)
                .lastestDate(null)
                .build();

        list.add(tumbler1);
        list.add(tumbler2);
        return list;
    }

    @GetMapping("/items/ecobags")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<Item> GetEcobagListByUserId(@RequestParam("userid") Long userId) {
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
                .lastestDate(null)
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
                .lastestDate(null)
                .build();
        list.add(ecobag1);
        list.add(ecobag2);
        return list;
    }

    @PostMapping("/items/tumblers")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public void AddTumbler(@RequestBody() Object body) {
        System.out.println(body);
    }

    @PostMapping("/items/ecobags")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public void AddEcobags(@RequestBody() Object body) {
        System.out.println(body);
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
