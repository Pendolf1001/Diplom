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


@RequiredArgsConstructor
@Controller
public class MainController {

    private final ProductService productService;
    private final MenuService menuService;
    private final OrderService orderService;


    @GetMapping("/")
    public String home(Model model) {
        // Получаем список всех меню из сервиса
        List<Menu> menus = menuService.getAllMenus();
        // Передаем список меню в модель
        model.addAttribute("menus", menus);
        model.addAttribute("message", "Добро пожаловать в наш ресторан!");
        return "index"; // Имя шаблона без расширения .html
    }


    @GetMapping("/menu")
    public String menu(@RequestParam(required = false) Long mid, HttpSession session, Model model) {
        // Если menuId не указан, используем значение из сессии
        Long menuId = (mid != null) ? mid : (Long) session.getAttribute("currentMenuId");

        // Если menuId всё ещё null, используем значение по умолчанию (например, 1)
        if (menuId == null) {
            menuId = 1L;
        }

        // Сохраняем menuId в сессии
        session.setAttribute("currentMenuId", menuId);

        // Получаем элементы меню по menuId
        List<MenuItem> menuItems = menuService.getMenuItems(menuId);
        // Инициализация корзины, если её нет в сессии
        List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        double total = cart != null ? calculateTotal(cart) : 0.0; // Вычисляем сумму
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("total", total); // Передаем сумму в модель
        return "menu";
    }

    private double calculateTotal(List<MenuItem> cart) {
        return cart.stream().mapToDouble(MenuItem::getPrice).sum();
    }


    @GetMapping("/view")
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrdersWithTotals());
        return "orders"; // Шаблон orders.html
    }


}



