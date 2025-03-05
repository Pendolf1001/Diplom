package com.example.diplom.service;

import com.example.diplom.model.Menu;
import com.example.diplom.model.MenuItem;
import com.example.diplom.repositories.MenuItemRepository;
import com.example.diplom.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    // Создать меню
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }



    // Добавить продукт в меню
    public MenuItem addItemToMenu(Long menuId, MenuItem item) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Меню не найдено"));
        item.setMenu(menu);
        return menuItemRepository.save(item);
    }

    // Получить все продукты из меню
    public List<MenuItem> getMenuItems(Long menuId) {

        return menuItemRepository.findByMenuId(menuId);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Меню не найдено"));
        return menu;
    }

    public Optional<MenuItem> getMenuItemById(Long menuItemId) {
       return menuItemRepository.findById(menuItemId);

    }
}