package com.example.diplom.controllers;


import com.example.diplom.model.MenuItem;
import com.example.diplom.service.MenuService;
import com.example.diplom.service.OrderService;
import com.example.diplom.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MainController {

    private final ProductService productService;
    private final MenuService menuService;
    private final OrderService orderService;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Добро пожаловать в наш ресторан!");
        return "index"; // Имя шаблона без расширения .html
    }

//    @GetMapping("/menu")
//    public String menu(Model model) {
//        model.addAttribute("menuItems",  menuService.getMenuItems(1L));
//        return "menu"; // Имя шаблона без расширения .html
//    }
    @GetMapping("/menu")
    public String menu(HttpSession session, Model model) {
        List<MenuItem> menuItems = menuService.getMenuItems(1L);
        List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cart");
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



