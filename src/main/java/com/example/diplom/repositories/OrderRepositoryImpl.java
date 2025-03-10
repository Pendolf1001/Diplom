package com.example.diplom.repositories;

import com.example.diplom.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с заказами.
 * Объединяет методы JpaRepository и кастомную логику.
 */

@Repository
public class OrderRepositoryImpl implements OrderRepository{


    @Autowired
    private OrderJpaRepository orderJpaRepository;

    /**
     * Сохраняет новый заказ
     * @param order Заказ для сохранения
     * @return Сохраненный заказ
     */
    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    /**
     * Ищет заказ по ID
     * @param id Идентификатор заказа
     * @return Optional с заказом или ошибкой, если не найден
     * @throws RuntimeException при отсутствии заказа
     */

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orderJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден")));
    }



    @Override
    public void deleteById(Long id) {
        orderJpaRepository.deleteById(id);
    }

    @Override
    public Order update(Order order) {
        return orderJpaRepository.save(order); // save() обновляет, если объект уже существует
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll();
    }
}
