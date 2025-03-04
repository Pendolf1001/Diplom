package com.example.diplom.controllers;

import com.example.diplom.model.*;
import com.example.diplom.service.MenuService;
import com.example.diplom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        return ResponseEntity.ok(menuService.createMenu(menu));
    }




    /**
     * Добавляет пиццу в меню.
     *
     * @param menuId ID меню.
     * @param pizza  Данные пиццы.
     * @return Сохраненная пицца.
     */
    @PostMapping("/{menuId}/pizzas")
    public ResponseEntity<PizzaMenuItem> addPizzaToMenu(
            @PathVariable Long menuId,
            @RequestBody PizzaMenuItem pizza
    ) {

        PizzaMenuItem savedPizza;
        try {
            savedPizza = (PizzaMenuItem) menuService.addItemToMenu(menuId, pizza);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PizzaMenuItem());
        }
        return new ResponseEntity<>(savedPizza, HttpStatus.CREATED);
    }



    @GetMapping("/{menuId}/items")
    public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable Long menuId) {
//        return ResponseEntity.ok(menuService.getMenuItems(menuId));


        try {
            Menu menuById=menuService.getMenuById(menuId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }

        return ResponseEntity.ok(menuService.getMenuItems(menuId));
    }


    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        return new ResponseEntity<>(menuService.getAllMenus(),HttpStatus.OK);
    }
}


