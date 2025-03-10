package com.example.diplom.repositories;

import com.example.diplom.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    /**
     * Ищет все элементы меню, связанные с указанным меню по его ID.
     *
     * @param menuId ID меню для фильтрации элементов
     * @return Список элементов меню, принадлежащих указанному меню
     */
    List<MenuItem> findByMenuId(Long menuId);
}