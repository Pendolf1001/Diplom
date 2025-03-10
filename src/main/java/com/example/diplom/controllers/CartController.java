package com.example.diplom.controllers;

import com.example.diplom.model.*;
import com.example.diplom.service.MenuService;
import com.example.diplom.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * Контроллер для управления корзиной и заказами.
 * Обрабатывает операции добавления/удаления товаров, оформления заказов
 * и просмотра списка заказов (для сотрудников).
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final OrderService orderService;
    private final MenuService menuService;

    /**
     * Отображает страницу корзины.
     * Вычисляет общую стоимость товаров и передает данные в представление.
     *
     * @param session Объект сессии для получения корзины
     * @param model   Модель для передачи данных в представление
     * @return Шаблон страницы корзины (cart.html)
     */
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<MenuItem> cart = getCart(session);
        double total = calculateTotal(cart);

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    /**
     * Добавляет товар в корзину.
     * Сохраняет выбранный товар в сессии и перенаправляет на страницу меню.
     *
     * @param menuItemId ID добавляемого товара
     * @param session    Объект сессии для хранения корзины
     * @return Редирект на страницу меню с текущим menuId
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam Long menuItemId, HttpSession session) {
        Long menuId = (Long) session.getAttribute("currentMenuId");
        List<MenuItem> cart = Optional.ofNullable((List<MenuItem>) session.getAttribute("cart"))
                .orElse(new ArrayList<>());

        MenuItem menuItem = menuService.getMenuItemById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Блюдо не найдено"));

        cart.add(menuItem);
        session.setAttribute("cart", cart);
        return "redirect:/menu?mid=" + menuId;
    }

    /**
     * Удаляет товар из корзины.
     *
     * @param menuItemId ID удаляемого товара
     * @param session    Объект сессии для обновления корзины
     * @return Редирект на страницу меню с текущим menuId
     */
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long menuItemId, HttpSession session) {
        Long menuId = (Long) session.getAttribute("currentMenuId");
        List<MenuItem> cart = getCart(session);

        cart.removeIf(item -> item.getId().equals(menuItemId));
        session.setAttribute("cart", cart);

        return "redirect:/menu?mid=" + menuId;
    }

    /**
     * Оформляет заказ и очищает корзину.
     * Конвертирует элементы меню в продукты и сохраняет заказ.
     *
     * @param session           Объект сессии для получения корзины
     * @param redirectAttributes Атрибуты для передачи сообщений об ошибках/успехе
     * @return Редирект на страницу меню с сообщением результата
     */
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

        session.removeAttribute("cart");
        redirectAttributes.addFlashAttribute("success", "Заказ успешно оформлен!");
        return "redirect:/menu";
    }

    /**
     * Отображает список всех заказов (для сотрудников и администраторов).
     *
     * @param model Модель для передачи списка заказов
     * @return Шаблон списка заказов (orders.html)
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public String viewAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrdersWithTotals());
        return "orders";
    }

    // ==== Вспомогательные методы ====

    /**
     * Получает корзину из сессии или создает новую.
     *
     * @param session Объект сессии
     * @return Список товаров в корзине
     */
    private List<MenuItem> getCart(HttpSession session) {
        List<MenuItem> cart = (List<MenuItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    /**
     * Вычисляет общую стоимость товаров в корзине.
     *
     * @param cart Список товаров
     * @return Общая сумма заказа
     */
    private double calculateTotal(List<MenuItem> cart) {
        return cart.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    /**
     * Конвертирует элементы меню в продукты с привязкой к меню.
     *
     * @param menuItems Список элементов меню
     * @return Список продуктов для сохранения в заказе
     * @throws IllegalArgumentException Если тип блюда не поддерживается
     * @throws IllegalStateException    Если элемент меню не привязан к меню
     */
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

                    if (item.getMenu() == null) {
                        throw new IllegalStateException("MenuItem не имеет связанного меню");
                    }
                    product.setMenuId(item.getMenu().getId());
                    return product;
                })
                .collect(Collectors.toList());
    }
}