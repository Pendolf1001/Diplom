package com.example.diplom.controllers;


import com.example.diplom.model.Menu;
import com.example.diplom.model.MenuItem;
import com.example.diplom.service.MenuService;
import com.example.diplom.service.OrderService;
import com.example.diplom.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

// MainController.java
/**
 * Основной контроллер для главной страницы и меню.
 */
@RequiredArgsConstructor
@Controller
public class MainController {
    private final ProductService productService;
    private final MenuService menuService;
    private final OrderService orderService;

    /**
     * Отображает домашнюю страницу со списком меню.
     *
     * @param model Модель для передачи списка меню
     * @return Шаблон главной страницы (index.html)
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Menu> menus = menuService.getAllMenus();
        model.addAttribute("menus", menus);
        model.addAttribute("message", "Добро пожаловать в наш ресторан!");
        return "index";
    }

    /**
     * Отображает меню по указанному ID или из сессии.
     *
     * @param mid      ID меню (опционально)
     * @param session  Объект сессии для хранения текущего menuId
     * @param model    Модель для передачи элементов меню и общей суммы
     * @return Шаблон меню (menu.html)
     */
    @GetMapping("/menu")
    public String menu(@RequestParam(required = false) Long mid, HttpSession session, Model model) {
        Long menuId = (mid != null) ? mid : (Long) session.getAttribute("currentMenuId");
        if (menuId == null) {
            menuId = 1L;
        }
        session.setAttribute("currentMenuId", menuId);
        List<MenuItem> menuItems = menuService.getMenuItems(menuId);
        List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        double total = cart != null ? calculateTotal(cart) : 0.0;
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("total", total);
        return "menu";
    }

    private double calculateTotal(List<MenuItem> cart) {
        return cart.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    /**
     * Отображает список заказов.
     *
     * @param model Модель для передачи списка заказов
     * @return Шаблон списка заказов (orders.html)
     */
    @GetMapping("/view")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrdersWithTotals());
        return "orders";
    }
}