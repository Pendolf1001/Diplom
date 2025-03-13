package com.example.diplom.service;

import com.example.diplom.dto.OrderResponse;
import com.example.diplom.model.*;
import com.example.diplom.repositories.MenuItemRepository;
import com.example.diplom.repositories.OrderRepository;
import com.example.diplom.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



/**
 * Сервис для управления заказами и их продуктами.
 */
@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final MenuItemRepository menuItemRepository;
    private final MenuService menuService;


    /**
     * Создает новый заказ.
     *
     * @param order заказ для сохранения
     * @return созданный заказ
     */
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }


    /**
     * Возвращает заказ по его ID.
     *
     * @param id ID заказа
     * @return заказ
     * @throws RuntimeException если заказ не найден
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> {
                log.warn("Заказ с ID {} не найден", id);
                return new RuntimeException("Заказ не найден");
                });
    }


    /**
     * Возвращает все заказы.
     *
     * @return список всех заказов
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    /**
     * Удаляет заказ по его ID.
     *
     * @param id ID заказа
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    /**
     * Обновляет существующий заказ.
     *
     * @param order заказ для обновления
     * @return обновленный заказ
     */
    public Order updateOrder(Order order) {
        return orderRepository.update(order);
    }

    /**
     * Добавляет продукт из меню в заказ.
     *
     * @param orderId      ID заказа
     * @param menuItemId   ID пункта меню
     * @return обновленный заказ
     * @throws RuntimeException если заказ или пункт меню не найдены
     */

    @Transactional
    public Order addProductFromMenu(Long orderId, Long menuItemId) {

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Продукт в меню не найден"));


        Product product = createProductFromMenuItem(menuItem);
        product.setMenuId(menuItem.getMenu().getId());


        Product savedProduct = productRepository.save(product);


        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.addProduct(savedProduct);

        return orderRepository.save(order);
    }


    /**
     * Создает продукт на основе элемента меню, копируя его свойства.
     * Поддерживает различные типы меню (PizzaMenuItem, RollMenuItem и др.).
     * Для каждого типа создается соответствующий продукт с сохранением специфичных характеристик.
     *
     * @param item элемент меню, на основе которого создается продукт
     * @return новый продукт с данными из элемента меню
     * @throws IllegalArgumentException если тип элемента меню не поддерживается
     */
    private Product createProductFromMenuItem(MenuItem item) {

        if (item instanceof PizzaMenuItem) {
            PizzaMenuItem pizzaItem = (PizzaMenuItem) item;
            Pizza pizza = new Pizza();
            pizza.setName(pizzaItem.getName());
            pizza.setDescription(pizzaItem.getDescription());
            pizza.setPrice(pizzaItem.getPrice());
            pizza.setDiameter(pizzaItem.getDiameter());
            pizza.setMenuId(item.getMenu().getId());
            return pizza;
        }else if (item instanceof RollMenuItem) {
            RollMenuItem rollItem = (RollMenuItem) item;
            RollDish roll = new RollDish();
            roll.setName(rollItem.getName());
            roll.setDescription(rollItem.getDescription());
            roll.setPrice(rollItem.getPrice());
            roll.setPieceCount(rollItem.getPieceCount()); // Только pieceCount
            roll.setMenuId(item.getMenu().getId());
            return roll;
        }

        // Аналогично для других типов
        throw new IllegalArgumentException("Тип продукта не поддерживается");
    }



    /**
     * Подсчитывает сумму заказа.
     *
     * @param orderId ID заказа.
     * @return Сумма заказа.
     */
    public double calculateOrderTotal(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        return order.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }


    /**
     * Преобразует заказ в DTO ответа.
     *
     * @param orderById заказ
     * @param total     сумма заказа
     * @return DTO ответа
     */
    public OrderResponse orderToOrderResponse(Order orderById, double total) {

        OrderResponse response = new OrderResponse();
        response.setId(orderById.getId());
        response.setStatus(orderById.getStatus());
        response.setProducts(orderById.getProducts());
        response.setTotal(total);
        return response;
    }


    /**
     * Возвращает все заказы с суммами и информацией о ресторане.
     *
     * @return список DTO заказов
     */
    public List<OrderResponse> getAllOrdersWithTotals() {
        List<Order> orders=orderRepository.findAll();
        return orders.stream()
                .map(order -> {
                    double total = calculateOrderTotal(order.getId());
                    OrderResponse response = orderToOrderResponse(order, total);
                    // Проверяем, есть ли продукты в заказе
                    if (!order.getProducts().isEmpty()) {
                        Product firstProduct = order.getProducts().get(0);
                        // Проверяем, что menuId не null
                        if (firstProduct.getMenuId() != null) {
                            try {
                                Menu menu = menuService.getMenuById(firstProduct.getMenuId());
                                response.setRestaurantName(menu.getName());
                                response.setRestaurantAddress(menu.getAddress());
                            } catch (RuntimeException e) {
                                // Логируем ошибку, если меню не найдено
                                log.error("Меню с ID {} не найдено", firstProduct.getMenuId());
                                response.setRestaurantName("Неизвестный ресторан");
                                response.setRestaurantAddress("Адрес не указан");
                            }
                        } else {
                            log.warn("Продукт {} не имеет menuId", firstProduct.getId());
                            response.setRestaurantName("Неизвестный ресторан");
                            response.setRestaurantAddress("Адрес не указан");
                        }
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }
}
