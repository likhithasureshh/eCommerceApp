-- Insert orders
INSERT INTO orders (total_price, order_status)
VALUES (1099.49, 'CONFIRMED');

INSERT INTO orders (total_price, order_status)
VALUES (649.49, 'CONFIRMED');

INSERT INTO orders (total_price, order_status)
VALUES (999.99, 'DELIVERED');

-- Order 1: Laptop (productId 1) + Mouse (productId 3)
INSERT INTO ordersitems (product_id, quantity, orders_id)
VALUES (1, 1, 1);

INSERT INTO ordersitems (product_id, quantity, orders_id)
VALUES (3, 4, 1);

-- Order 2: Keyboard (productId 2) + Headphones (productId 5)
INSERT INTO ordersitems (product_id, quantity, orders_id)
VALUES (2, 1, 2);

INSERT INTO ordersitems (product_id, quantity, orders_id)
VALUES (5, 2, 2);

-- Order 3: Laptop (productId 1)
INSERT INTO ordersitems (product_id, quantity, orders_id)
VALUES (1, 1, 3);