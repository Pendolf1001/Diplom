package com.example.diplom.service;

import com.example.diplom.model.Menu;
import com.example.diplom.model.MenuItem;
import com.example.diplom.repositories.MenuItemRepository;
import com.example.diplom.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления меню и его пунктами.
 */
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    /**
     * Создает новое меню.
     *
     * @param menu объект меню для сохранения
     * @return сохраненное меню
     */
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    /**
     * Добавляет пункт меню в указанное меню.
     *
     * @param menuId ID меню
     * @param item   пункт меню для добавления
     * @return сохраненный пункт меню
     * @throws RuntimeException если меню не найдено
     */
    public MenuItem addItemToMenu(Long menuId, MenuItem item) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Меню не найдено"));
        item.setMenu(menu);
        return menuItemRepository.save(item);
    }

    /**
     * Возвращает все пункты меню по ID меню.
     *
     * @param menuId ID меню
     * @return список пунктов меню
     */
    public List<MenuItem> getMenuItems(Long menuId) {
        return menuItemRepository.findByMenuId(menuId);
    }

    /**
     * Возвращает все меню.
     *
     * @return список всех меню
     */
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    /**
     * Возвращает меню по его ID.
     *
     * @param menuId ID меню
     * @return меню
     * @throws RuntimeException если меню не найдено
     */
    public Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Меню не найдено"));
    }

    /**
     * Возвращает пункт меню по его ID.
     *
     * @param menuItemId ID пункта меню
     * @return Optional с пунктом меню или пустой
     */
    public Optional<MenuItem> getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId);
    }
}