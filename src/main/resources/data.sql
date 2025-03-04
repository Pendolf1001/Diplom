


--INSERT INTO products (name, description, price, status, diameter, dtype) VALUES
--('Маргарита', 'Классическая итальянская пицца с томатами и моцареллой', 9.99, 'NOT_STARTED',  30, 'PIZZA'),
--('Пепперони', 'Острая пицца с колбасками пепперони', 11.99, 'NOT_STARTED', 30, 'PIZZA'),
--('Четыре сыра', 'Пицца с сырами моцарелла, пармезан, дор блю и чеддер', 12.99, 'NOT_STARTED', 35, 'PIZZA'),
--('Гавайская', 'Пицца с ветчиной и ананасом', 10.99, 'NOT_STARTED',  30, 'PIZZA'),
--('Карбонара', 'Пицца с беконом, сливочным соусом и яйцом', 13.99, 'NOT_STARTED', 35, 'PIZZA'),
--('Диабло', 'Очень острая пицца с перцем чили и салями', 12.99, 'NOT_STARTED',  30, 'PIZZA'),
--('Вегетарианская', 'Пицца с овощами и грибами', 10.99, 'NOT_STARTED',  30, 'PIZZA'),
--('Мясная', 'Пицца с ветчиной, беконом, колбасой и пепперони', 14.99, 'NOT_STARTED', 35, 'PIZZA'),
--('Морепродукты', 'Пицца с креветками, мидиями и кальмарами', 15.99, 'NOT_STARTED', 35, 'PIZZA'),
--('Трюфельная', 'Элитная пицца с трюфельным маслом и пармезаном', 18.99, 'NOT_STARTED', 30, 'PIZZA');
--
---- Заполнение таблицы orders
--INSERT INTO orders ( status) VALUES
--('NEW');
--
---- Заполнение таблицы order_products (связь заказов и продуктов)
--INSERT INTO ORDERS_PRODUCTS (order_id, product_id) VALUES
--(1, 1),
--(1, 2);

---- Очистка таблиц (для тестовых данных)
--DELETE FROM order_products;
--DELETE FROM products;
--DELETE FROM orders;
--DELETE FROM menu_items;
--DELETE FROM menus;

---- Заполнение таблицы меню
---- Очистка таблиц (для тестовых данных)
--DELETE FROM orders_products;
--DELETE FROM products;
--DELETE FROM orders;
--DELETE FROM menu_items;
--DELETE FROM menus;

-- Заполнение таблицы меню
INSERT INTO menus (name, address, description) VALUES
('Основное меню', 'ул. Пушкина, 42', 'Меню ресторана "Итальянская кухня"');

-- Заполнение таблицы элементов меню (MenuItem)
--INSERT INTO menu_items (name, description, price, diameter, dtype, menu_id) VALUES
--('Маргарита', 'Классическая итальянская пицца с томатами и моцареллой', 9.99, 30, 'PIZZA', 1),
--('Пепперони', 'Острая пицца с колбасками пепперони', 11.99, 30, 'PIZZA', 1),
--('Четыре сыра', 'Пицца с сырами моцарелла, пармезан, дор блю и чеддер', 12.99, 35, 'PIZZA', 1),
--('Гавайская', 'Пицца с ветчиной и ананасом', 10.99, 30, 'PIZZA', 1),
--('Карбонара', 'Пицца с беконом, сливочным соусом и яйцом', 13.99, 35, 'PIZZA', 1),
--('Диабло', 'Очень острая пицца с перцем чили и салями', 12.99, 30, 'PIZZA', 1),
--('Вегетарианская', 'Пицца с овощами и грибами', 10.99, 30, 'PIZZA', 1),
--('Мясная', 'Пицца с ветчиной, беконом, колбасой и пепперони', 14.99, 35, 'PIZZA', 1),
--('Морепродукты', 'Пицца с креветками, мидиями и кальмарами', 15.99, 35, 'PIZZA', 1),
--('Трюфельная', 'Элитная пицца с трюфельным маслом и пармезаном', 18.99, 30, 'PIZZA', 1);
INSERT INTO menu_items (name, description, price, diameter, piece_count, dtype, menu_id) VALUES
-- Пиццы
('Маргарита', 'Классическая итальянская пицца с томатами и моцареллой', 9.99, 30, NULL, 'PIZZA', 1),
('Пепперони', 'Острая пицца с колбасками пепперони', 11.99, 30, NULL, 'PIZZA', 1),
('Четыре сыра', 'Пицца с сырами моцарелла, пармезан, дор блю и чеддер', 12.99, 35, NULL, 'PIZZA', 1),
('Гавайская', 'Пицца с ветчиной и ананасом', 10.99, 30, NULL, 'PIZZA', 1),
('Карбонара', 'Пицца с беконом, сливочным соусом и яйцом', 13.99, 35, NULL, 'PIZZA', 1),
('Диабло', 'Очень острая пицца с перцем чили и салями', 12.99, 30, NULL, 'PIZZA', 1),
('Вегетарианская', 'Пицца с овощами и грибами', 10.99, 30, NULL, 'PIZZA', 1),
('Мясная', 'Пицца с ветчиной, беконом, колбасой и пепперони', 14.99, 35, NULL, 'PIZZA', 1),
('Морепродукты', 'Пицца с креветками, мидиями и кальмарами', 15.99, 35, NULL, 'PIZZA', 1),
('Трюфельная', 'Элитная пицца с трюфельным маслом и пармезаном', 18.99, 30, NULL, 'PIZZA', 1),

-- Роллы
('Филадельфия', 'Ролл с лососем и сливочным сыром', 14.99, NULL, 8, 'ROLL', 1),
('Калифорния', 'Ролл с крабом и авокадо', 12.99, NULL, 6, 'ROLL', 1),
('Дракон', 'Ролл с угрем и авокадо', 16.99, NULL, 8, 'ROLL', 1),
('Аляска', 'Ролл с лососем, огурцом и авокадо', 13.99, NULL, 6, 'ROLL', 1),
('Бонито', 'Ролл с тунцом и стружкой тунца', 15.99, NULL, 8, 'ROLL', 1),
('Гейша', 'Ролл с креветкой и спайси соусом', 14.99, NULL, 6, 'ROLL', 1),
('Токио', 'Ролл с лососем, тунцом и авокадо', 17.99, NULL, 8, 'ROLL', 1),
('Сакура', 'Ролл с крабом, огурцом и икрой', 18.99, NULL, 6, 'ROLL', 1),
('Ясай', 'Вегетарианский ролл с авокадо и огурцом', 11.99, NULL, 6, 'ROLL', 1),
('Унаги', 'Ролл с угрем и огурцом', 19.99, NULL, 8, 'ROLL', 1);

-- Заполнение таблицы заказов
INSERT INTO orders (status) VALUES
( 'NEW');

-- Заполнение таблицы продуктов (для заказов)
INSERT INTO products (dtype, name, description, price, diameter, status, menu_id) VALUES
( 'PIZZA', 'Маргарита', 'Классическая пицца с томатами и моцареллой', 9.99, 30, 'NOT_STARTED', 1),
('PIZZA', 'Пепперони', 'Острая пицца с колбасками пепперони', 11.99, 30, 'NOT_STARTED', 1);

-- Связь заказов и продуктов
INSERT INTO orders_products (order_id, product_id) VALUES
(1, 1),
(1, 2);