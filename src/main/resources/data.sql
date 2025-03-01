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


-- Заполнение таблицы products
INSERT INTO products (id, name, description, price, status, product_type, dtype) VALUES
(1, 'Пицца', 'Сырная пицца с томатами', 10.99, 'NOT_STARTED', 'DISH', 'DISH'),
(2, 'Паста', 'Сырная пицца с томатами', 12.00, 'NOT_STARTED', 'DISH', 'DISH');

-- Заполнение таблицы orders
INSERT INTO orders (id, status) VALUES
(1, 'NEW');

-- Заполнение таблицы order_products (связь заказов и продуктов)
INSERT INTO ORDERS_PRODUCTS (order_id, product_id) VALUES
(1, 1),
(1, 2);