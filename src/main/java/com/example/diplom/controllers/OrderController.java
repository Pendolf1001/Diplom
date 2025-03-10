package com.example.diplom.controllers;


//import com.example.diplom.cases.AddProductToOrderUseCase;
import com.example.diplom.dto.OrderResponse;
import com.example.diplom.model.Order;

import com.example.diplom.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * REST-контроллер для управления заказами.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
class OrderController {
    private final OrderService orderService;

    /**
     * Создает новый заказ.
     *
     * @param order Объект заказа для создания
     * @return Созданный заказ в теле ответа
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    /**
     * Получает заказ по ID с общей суммой.
     *
     * @param id ID заказа
     * @return Заказ с общей суммой в DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order orderById;
        try {
            orderById = orderService.getOrderById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderResponse());
        }
        double total = orderService.calculateOrderTotal(id);
        OrderResponse response = orderService.orderToOrderResponse(orderById, total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Получает все заказы с суммами.
     *
     * @return Список заказов с суммами в теле ответа
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrdersWithTotals(), HttpStatus.OK);
    }

    /**
     * Удаляет заказ по ID.
     *
     * @param id ID заказа
     * @return Пустой ответ при успехе
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет существующий заказ.
     *
     * @param id    ID заказа
     * @param order Обновленные данные заказа
     * @return Обновленный заказ в теле ответа
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        try {
            return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn("Заказ с таким Id не найден");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }
    }

    /**
     * Добавляет продукт из меню в заказ.
     *
     * @param orderId      ID заказа
     * @param menuItemId ID элемента меню
     * @return Обновленный заказ в теле ответа
     */
    @PostMapping("/{orderId}/menu-items/{menuItemId}")
    public ResponseEntity<Order> addProductFromMenu(
            @PathVariable Long orderId,
            @PathVariable Long menuItemId) {
        try {
            Order updatedOrder = orderService.addProductFromMenu(orderId, menuItemId);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.warn("Ошибка при добавлении продукта в заказ: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Order());
        }
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

    /**
     * Добавляет товар в текущий заказ (упрощенная версия).
     *
     * @param menuItemId ID элемента меню
     * @param attributes Атрибуты для сообщений
     * @return Редирект на меню с уведомлением
     */
    @PostMapping("/add")
    public String addToOrder(
            @RequestParam Long menuItemId,
            RedirectAttributes attributes) {
        try {
            Order order = orderService.createOrder(new Order());
            orderService.addProductFromMenu(order.getId(), menuItemId);
            attributes.addFlashAttribute("success", "Блюдо добавлено в заказ!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Ошибка: " + e.getMessage());
        }
        return "redirect:/menu";
    }
}
