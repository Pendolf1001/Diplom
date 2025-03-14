
---- Создание таблицы меню
--CREATE TABLE IF NOT EXISTS menus (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(255) NOT NULL,
--    address VARCHAR(255),         -- Адрес заведения
--    description VARCHAR(500)
--);
--
---- Создание таблицы элементов меню (MenuItem)
--CREATE TABLE IF NOT EXISTS menu_items (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(255) NOT NULL,
--    description VARCHAR(500),
--    price DOUBLE NOT NULL,
--    diameter INT,                 -- Специфичное поле для пиццы
--    dtype VARCHAR(50) NOT NULL,   -- Дискриминатор (PIZZA, PASTA и т.д.)
--    menu_id BIGINT,               -- Ссылка на меню
--    FOREIGN KEY (menu_id) REFERENCES menus(id)
--);
--
---- Создание таблицы заказов
--CREATE TABLE IF NOT EXISTS orders (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    status VARCHAR(50) NOT NULL
--);
--
---- Создание таблицы продуктов (для заказов)
--CREATE TABLE IF NOT EXISTS products (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    dtype VARCHAR(50) NOT NULL,   -- Дискриминатор (PIZZA, PASTA и т.д.)
--    name VARCHAR(255) NOT NULL,
--    description VARCHAR(500),
--    price DOUBLE NOT NULL,
--    diameter INT,                 -- Специфичное поле для пиццы
--    sauce_type VARCHAR(100),      -- Специфичное поле для пасты
--    status VARCHAR(50) NOT NULL,
--    menu_id BIGINT                -- ID меню, из которого добавлен продукт
--);
--
---- Создание таблицы связи заказов и продуктов
--CREATE TABLE IF NOT EXISTS orders_products (
--    order_id BIGINT,
--    product_id BIGINT,
--    FOREIGN KEY (order_id) REFERENCES orders(id),
--    FOREIGN KEY (product_id) REFERENCES products(id)
--);



-- Создание таблицы меню
CREATE TABLE IF NOT EXISTS menus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),         -- Адрес заведения
    description VARCHAR(500)
);

-- Создание таблицы элементов меню (MenuItem)
CREATE TABLE IF NOT EXISTS menu_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dtype VARCHAR(50) NOT NULL,   -- Дискриминатор (PIZZA, ROLL и т.д.)
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price DOUBLE NOT NULL,
    diameter INT,                 -- Специфичное поле для пиццы
    piece_count INT,              -- Специфичное поле для роллов
    menu_id BIGINT,               -- Ссылка на меню
    FOREIGN KEY (menu_id) REFERENCES menus(id)
);

-- Создание таблицы заказов
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

-- Создание таблицы продуктов (для заказов)
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dtype VARCHAR(50) NOT NULL,   -- Дискриминатор (PIZZA, ROLL и т.д.)
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price DOUBLE NOT NULL,
    diameter INT,                 -- Специфичное поле для пиццы
    piece_count INT,              -- Специфичное поле для роллов
    status VARCHAR(50) NOT NULL,
    menu_id BIGINT                -- ID меню, из которого добавлен продукт
);

-- Создание таблицы связи заказов и продуктов
CREATE TABLE IF NOT EXISTS orders_products (
    order_id BIGINT,
    product_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

--- Таблица пользователей
 CREATE TABLE IF NOT EXISTS users (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     username VARCHAR(255) NOT NULL,
     password VARCHAR(255) NOT NULL
 );

 -- Таблица ролей
 CREATE TABLE IF NOT EXISTS roles (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255) NOT NULL
 );

 -- Связующая таблица для many-to-many
CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);