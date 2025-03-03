--insert into orders (name, status)
--values ('Vasya' , 'NOT_STARTED');
--
--
--insert into orders (name, status)
--values ('Petya' , 'COMPLETED');
--
--
--insert into pizzas (name, status, order_id)
--values ('Margarita' , 'NOT_STARTED',1);
--
--insert into pizzas (name, status, order_id)
--values ('Gavayskaya' , 'IN_PROGRESS',2);
--
--insert into pizzas (name, status, order_id)
--values ('Verona' , 'COMPLETED',2);


INSERT INTO products (id, name, description, price, status, product_type, diameter, dtype) VALUES
(1, 'Маргарита', 'Классическая итальянская пицца с томатами и моцареллой', 9.99, 'NOT_STARTED', 'DISH', 30, 'PIZZA'),
(2, 'Пепперони', 'Острая пицца с колбасками пепперони', 11.99, 'NOT_STARTED', 'DISH', 30, 'PIZZA'),
(3, 'Четыре сыра', 'Пицца с сырами моцарелла, пармезан, дор блю и чеддер', 12.99, 'NOT_STARTED', 'DISH', 35, 'PIZZA'),
(4, 'Гавайская', 'Пицца с ветчиной и ананасом', 10.99, 'NOT_STARTED', 'DISH', 30, 'PIZZA'),
(5, 'Карбонара', 'Пицца с беконом, сливочным соусом и яйцом', 13.99, 'NOT_STARTED', 'DISH', 35, 'PIZZA'),
(6, 'Диабло', 'Очень острая пицца с перцем чили и салями', 12.99, 'NOT_STARTED', 'DISH', 30, 'PIZZA'),
(7, 'Вегетарианская', 'Пицца с овощами и грибами', 10.99, 'NOT_STARTED', 'DISH', 30, 'PIZZA'),
(8, 'Мясная', 'Пицца с ветчиной, беконом, колбасой и пепперони', 14.99, 'NOT_STARTED', 'DISH', 35, 'PIZZA'),
(9, 'Морепродукты', 'Пицца с креветками, мидиями и кальмарами', 15.99, 'NOT_STARTED', 'DISH', 35, 'PIZZA'),
(10, 'Трюфельная', 'Элитная пицца с трюфельным маслом и пармезаном', 18.99, 'NOT_STARTED', 'DISH', 30, 'PIZZA');

-- Заполнение таблицы orders
INSERT INTO orders (id, status) VALUES
(1, 'NEW');

-- Заполнение таблицы order_products (связь заказов и продуктов)
INSERT INTO ORDERS_PRODUCTS (order_id, product_id) VALUES
(1, 1),
(1, 2);