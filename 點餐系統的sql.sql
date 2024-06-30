DROP TABLE IF EXISTS order_detail;
DROP TABLE IF EXISTS _order;
DROP TABLE IF EXISTS dish_entity;
DROP TABLE IF EXISTS dish_type;

CREATE TABLE dish_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE dish_entity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    type_id INT,
    FOREIGN KEY (type_id) REFERENCES dish_type(id)
);

CREATE TABLE _order (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_time DATETIME NOT NULL,
    total_price DOUBLE PRECISION,
    pay_method VARCHAR(50)
);

CREATE TABLE order_detail (
    order_id INT,
    dno INT,
    count INT NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    dish_id INT,
    PRIMARY KEY (order_id, dno),
    FOREIGN KEY (order_id) REFERENCES _order(id),
    FOREIGN KEY (dish_id) REFERENCES dish_entity(id)
);
