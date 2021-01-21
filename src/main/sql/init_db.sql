DROP TABLE IF EXISTS products;
CREATE TABLE products(
    id serial,
    name VARCHAR(100),
    description VARCHAR(400),
    default_price numeric,
    default_currency VARCHAR(3),
    category_id INT NOT NULL,
    supplier_id INT NOT NULL
);

DROP TABLE IF EXISTS product_categories;
CREATE TABLE product_categories(
    id serial,
    name VARCHAR(100),
    description VARCHAR(400),
    department VARCHAR(50)
);

DROP TABLE IF EXISTS suppliers;
CREATE TABLE suppliers(
    id serial,
    name VARCHAR(100),
    description VARCHAR(400)
);


DROP TABLE IF EXISTS users;
CREATE TABLE users(
    id serial,
    username VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(200)
);

DROP TABLE IF EXISTS carts;
CREATE TABLE carts(
    id serial,
    user_id INT NOT NULL,
    status_id INT NOT NULL
);

DROP TABLE IF EXISTS cart_statuses;
CREATE TABLE cart_statuses(
    id serial,
    status VARCHAR(30)
);

DROP TABLE IF EXISTS line_items;
CREATE TABLE line_items(
    id serial,
    quantity INT NOT NULL,
    product_id INT NOT NULL,
    cart_id INT NOT NULL
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
    id serial,
    cart_id INT NOT NULL,
    user_id INT NOT NULL,
    billing_address_id INT NOT NULL,
    shipping_address_id INT NOT NULL,
    status_id INT NOT NULL
);

DROP TABLE IF EXISTS order_statuses;
CREATE TABLE order_statuses(
    id serial,
    status VARCHAR(30)
);

DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses(
    id serial,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
    address VARCHAR(100),
    country VARCHAR(50),
    city VARCHAR(50),
    zip INT,
    phone VARCHAR(30),
    user_id INT
);

INSERT INTO
    product_categories (name, description, department)
VALUES
    ('Tablet', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.', 'Hardware'),
    ('TV', 'A TV Set, is a device that combines a tuner, display, and loudspeakers, for the purpose of viewing and hearing television.', 'Home Entertainment'),
    ('Smartphone', 'A smartphone is a mobile device that combines cellular and mobile computing functions into one unit.', 'Smartphone'),
    ('Laptop', 'A laptop or laptop computer, is a small, portable personal computer (PC) with a seashell form factor.', 'Hardware');

INSERT INTO
    suppliers(name, description)
VALUES
    ('Amazon', 'American multinational technology company based in Seattle, Washington, which focuses on e-commerce, cloud computing, digital streaming, and artificial intelligence. '),
    ('Lenovo', 'Chinese multinational technology company.'),
    ('Samsung', 'South Korean multinational conglomerate headquartered in Samsung Town, Seoul.'),
    ('Apple', 'American multinational technology company headquartered in Cupertino, California, that designs, develops, and sells consumer electronics, computer software, and online services.');

INSERT INTO
    products (name, description, default_price, default_currency, category_id, supplier_id)
VALUES
    ('Amazon Fire', 'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 49.99, 'USD', 1, 1),
    ('Lenovo IdeaPad Miix 700', 'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 479, 'USD', 1, 2),
    ('Amazon Fire HD 8', 'Amazon''s latest Fire HD 8 tablet is a great value for media consumption.', 89, 'USD', 1, 1),
    ('TV', 'Super cool television', 200, 'USD', 2, 3),
    ('Apple iPhone 12 Pro Max 128GB', 'Silver, Graphite, Gold, Pacific Blue. Ceramic Shield front. Textured matte glass back and stainless steel design', 800, 'USD', 3, 4),
    ('Samsung 65-inch Class QLED Q800T Series - Real 8K Resolution', 'This bundle includes the Samsung 65-inch Class QLED Q800T Series Smart TV with Alexa Built-In and the Amazon Smart Plug.', 3899, 'USD', 2, 3),
    ('Samsung Galaxy Note 20', 'The Dynamic AMOLED 2X Infinity-O Display on Galaxy Note20 Ultra 5G delivers 1500 nits for a colorful, glare-free view, even in bright daylight.1 And it reduces blue light significantly to lessen eye strain.', 470, 'USD', 3, 3),
    ('IPad Pro', 'Apple tablet', 800, 'USD', 1, 4),
    ('MacBook Pro', 'Apple M1 Chip with 8-Core CPU and 8-Core GPU, 512GB Storage', 1500, 'USD', 4, 4);

INSERT INTO
    users (username, email, password)
VALUES
    ('Ruptiliu', 'ruptilian@codecool.com', 'ruptix2021');
