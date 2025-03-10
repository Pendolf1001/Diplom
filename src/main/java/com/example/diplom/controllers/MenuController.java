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


/**
 * REST-контроллер для управления меню.
 */
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    /**
     * Создает новое меню.
     *
     * @param menu Объект меню для создания
     * @return Созданное меню в теле ответа
     */
    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        return ResponseEntity.ok(menuService.createMenu(menu));
    }

    /**
     * Добавляет пиццу в меню.
     *
     * @param menuId ID меню
     * @param pizza  Данные пиццы
     * @return Сохраненная пицца в теле ответа
     */
    @PostMapping("/{menuId}/pizzas")
    public ResponseEntity<PizzaMenuItem> addPizzaToMenu(
            @PathVariable Long menuId,
            @RequestBody PizzaMenuItem pizza) {
        PizzaMenuItem savedPizza;
        try {
            savedPizza = (PizzaMenuItem) menuService.addItemToMenu(menuId, pizza);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PizzaMenuItem());
        }
        return new ResponseEntity<>(savedPizza, HttpStatus.CREATED);
    }

    /**
     * Добавляет роллы в меню.
     *
     * @param menuId ID меню
     * @param roll   Данные ролла
     * @return Сохраненный ролл в теле ответа
     */
    @PostMapping("/{menuId}/rolls")
    public ResponseEntity<RollMenuItem> addRollsToMenu(
            @PathVariable Long menuId,
            @RequestBody RollMenuItem roll) {
        RollMenuItem savedRoll;
        try {
            savedRoll = (RollMenuItem) menuService.addItemToMenu(menuId, roll);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RollMenuItem());
        }
        return new ResponseEntity<>(savedRoll, HttpStatus.CREATED);
    }

    /**
     * Получает элементы меню по ID.
     *
     * @param menuId ID меню
     * @return Список элементов меню в теле ответа
     */
    @GetMapping("/{menuId}/items")
    public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable Long menuId) {
        try {
            Menu menuById = menuService.getMenuById(menuId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
        return ResponseEntity.ok(menuService.getMenuItems(menuId));
    }

    /**
     * Получает все меню.
     *
     * @return Список всех меню в теле ответа
     */
    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        return new ResponseEntity<>(menuService.getAllMenus(), HttpStatus.OK);
    }
}