--create table orders(
--    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(50) NOT NULL,
--    status VARCHAR(50) NOT NULL,
--    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
--
--);
--
--
--create table pizzas(
--    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--    name VARCHAR(50) NOT NULL,
--    discription VARCHAR(50) NOT NULL,
--    status VARCHAR(50) NOT NULL,
--    price
--    order_id INT
--);
-- Создание таблицы products
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    diameter INT, -- Новое поле для пицц
    dtype VARCHAR(50) NOT NULL
);

-- Создание таблицы orders
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

-- Создание таблицы order_products (связь заказов и продуктов)
CREATE TABLE IF NOT EXISTS ORDERS_PRODUCTS (
    order_id BIGINT,
    product_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);