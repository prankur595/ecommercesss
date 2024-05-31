-- Replace the id here with the first user id you want to have ownership of the orders.
SET @userId1 = 1;
-- Replace the id here with the second user id you want to have ownership of the orders.
SET @userId2 = 2;

DELETE FROM orders_quantity where id >= 1;
DELETE FROM orders_by_user where id >= 1;
DELETE FROM inventory where id >= 1;
DELETE FROM product where id >= 1;
DELETE FROM address where id >= 1;

INSERT INTO product (id,`name`, short_description, long_description, price) VALUES (1,'Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50);
INSERT INTO product (id,`name`, short_description, long_description, price) VALUES (2,'Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56);
INSERT INTO product (id,`name`, short_description, long_description, price) VALUES (3,'Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74);
INSERT INTO product (id,`name`, short_description, long_description, price) VALUES (4,'Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69);
INSERT INTO product (id,`name`, short_description, long_description, price) VALUES (5,'Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);

SET @product1 = 0;
SET @product2 = 0;
SET @product3 = 0;
SET @product4 = 0;
SET @product5 = 0;

SELECT @product1 := id FROM product WHERE `name` = 'Product #1';
SELECT @product2 := id FROM product WHERE `name` = 'Product #2';
SELECT @product3 := id FROM product WHERE name = 'Product #3';
SELECT @product4 := id FROM product WHERE name = 'Product #4';
SELECT @product5 := id FROM product WHERE name = 'Product #5';

INSERT INTO inventory (id, product_id, in_stock_quantity) VALUES (1, @product1, 5);
INSERT INTO inventory (id, product_id, in_stock_quantity) VALUES (2, @product2, 8);
INSERT INTO inventory (id, product_id, in_stock_quantity) VALUES (3, @product3, 12);
INSERT INTO inventory (id, product_id, in_stock_quantity) VALUES (4, @product4, 73);
INSERT INTO inventory (id, product_id, in_stock_quantity) VALUES (5, @product5, 2);

INSERT INTO address (id, address_line_1, city, country, user_id) VALUES (1, '123 Tester Hill', 'Testerton', 'England', @userId1);
INSERT INTO address (id, address_line_1, city, country, user_id) VALUES (2, '312 Spring Boot', 'Hibernate', 'England', @userId2);

SET @address1 = 0;
SET @address2 = 0;

SELECT @address1 := id FROM address WHERE user_id = @userId1 ORDER BY id DESC LIMIT 1;
SELECT @address2 := id FROM address WHERE user_id = @userId2 ORDER BY id DESC LIMIT 1;

INSERT INTO orders_by_user (id, address_id, user_id) VALUES (1, @address1, @userId1);
INSERT INTO orders_by_user (id, address_id, user_id) VALUES (2, @address1, @userId1);
INSERT INTO orders_by_user (id, address_id, user_id) VALUES (3, @address1, @userId1);
INSERT INTO orders_by_user (id, address_id, user_id) VALUES (4, @address2, @userId2);
INSERT INTO orders_by_user (id, address_id, user_id) VALUES (5, @address2, @userId2);

SET @order1 = 0;
SET @order2 = 0;
SET @order3 = 0;
SET @order4 = 0;
SET @order5 = 0;

SELECT @order1 := id FROM orders_by_user WHERE address_id = @address1 AND user_id = @userId1 ORDER BY id DESC LIMIT 1;
SELECT @order2 := id FROM orders_by_user WHERE address_id = @address1 AND user_id = @userId1 ORDER BY id DESC LIMIT 1 OFFSET 1;
SELECT @order3 := id FROM orders_by_user WHERE address_id = @address1 AND user_id = @userId1 ORDER BY id DESC LIMIT 1 OFFSET 2;
SELECT @order4 := id FROM orders_by_user WHERE address_id = @address2 AND user_id = @userId2 ORDER BY id DESC LIMIT 1;
SELECT @order5 := id FROM orders_by_user WHERE address_id = @address2 AND user_id = @userId2 ORDER BY id DESC LIMIT 1 OFFSET 1;

INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (1,@order1, @product1, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (2, @order1, @product2, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (3, @order2, @product3, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (4, @order2, @product2, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (5, @order2, @product5, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (6, @order3, @product3, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (7, @order4, @product4, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (8, @order4, @product2, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (9, @order5, @product3, 5);
INSERT INTO orders_quantity (id, order_id, product_id, quantity) VALUES (10, @order5, @product1, 5);