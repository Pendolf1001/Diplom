package com.example.diplom.controllers;

import com.example.diplom.model.*;
import com.example.diplom.service.MenuService;
import com.example.diplom.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final OrderService orderService;
    private final MenuService menuService;

    // Корзина (хранится в сессии)
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<MenuItem> cart = getCart(session);
        double total = calculateTotal(cart); // Вычисляем общую сумму

        System.out.println(total);
        model.addAttribute("cart", cart);
        model.addAttribute("total", total); // Передаем общую сумму в модель
        return "cart";
    }

    // Добавление блюда в корзину
    @PostMapping("/add")
    public String addToCart(@RequestParam Long menuItemId, HttpSession session) {
        Long menuId = (Long) session.getAttribute("currentMenuId");
        // Добавляем товар в корзину
        List<MenuItem> cart = Optional.ofNullable((List<MenuItem>) session.getAttribute("cart"))
                .orElse(new ArrayList<>());
        MenuItem menuItem = menuService.getMenuItemById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Блюдо не найдено"));
        cart.add(menuItem);
        session.setAttribute("cart", cart);

        // Возвращаемся на страницу меню с текущим menuId
        return "redirect:/menu?mid=" + menuId;
    }

    // Удаление блюда из корзины

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long menuItemId, HttpSession session) {
        // Получаем menuId из сессии
        Long menuId = (Long) session.getAttribute("currentMenuId");

        // Удаляем товар из корзины
        List<MenuItem> cart = getCart(session);
        cart.removeIf(item -> item.getId().equals(menuItemId));
        session.setAttribute("cart", cart);

        // Возвращаемся на страницу меню с текущим menuId
        return "redirect:/menu?mid=" + menuId;
    }

    // Оформление заказа
    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        List<MenuItem> cart = getCart(session);
        if (cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Корзина пуста!");
            return "redirect:/menu";
        }

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setProducts(convertMenuItemsToProducts(cart));
        orderService.createOrder(order);

        session.removeAttribute("cart"); // Очищаем корзину
        redirectAttributes.addFlashAttribute("success", "Заказ успешно оформлен!");
        return "redirect:/menu"; // Возвращаемся на страницу меню
    }

    // Просмотр всех заказов (для работника)
    @GetMapping("/all")
    public String viewAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrdersWithTotals());
        return "orders";
    }

    // Вспомогательные методы
    private List<MenuItem> getCart(HttpSession session) {
        List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private double calculateTotal(List<MenuItem> cart) {
        return cart.stream().mapToDouble(MenuItem::getPrice).sum();
    }


    private List<Product> convertMenuItemsToProducts(List<MenuItem> menuItems) {
        return menuItems.stream()
                .map(item -> {
                    Product product;
                    if (item instanceof PizzaMenuItem) {
                        PizzaMenuItem pizzaItem = (PizzaMenuItem) item;
                        product = new Pizza(
                                pizzaItem.getName(),
                                pizzaItem.getDescription(),
                                pizzaItem.getPrice(),
                                pizzaItem.getDiameter()
                        );
                    } else if (item instanceof RollMenuItem) {
                        RollMenuItem rollItem = (RollMenuItem) item;
                        product = new RollDish(
                                rollItem.getName(),
                                rollItem.getDescription(),
                                rollItem.getPrice(),
                                rollItem.getPieceCount()
                        );
                    } else {
                        throw new IllegalArgumentException("Неподдерживаемый тип блюда");
                    }

                    // Проверяем, что menuItem связан с Menu
                    if (item.getMenu() == null) {
                        throw new IllegalStateException("MenuItem не имеет связанного меню");
                    }
                    product.setMenuId(item.getMenu().getId());
                    return product;
                })
                .collect(Collectors.toList());
    }
}