CREATE TABLE tables (
  id INT AUTO_INCREMENT PRIMARY KEY,
  free BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE orders (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  table_id INT NOT NULL
);

CREATE TABLE bills (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  table_id INT NOT NULL,
  order_id INT NOT NULL UNIQUE
);

CREATE TABLE menu_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE stock (
  id INT AUTO_INCREMENT PRIMARY KEY,
  menu_item_id INT NOT NULL UNIQUE,
  count INT NOT NULL DEFAULT 0
);

CREATE TABLE events (
  id INT AUTO_INCREMENT PRIMARY KEY,
  payload TEXT NOT NULL,
  type TEXT NOT NULL
);

INSERT INTO tables (id, free) VALUES (1, 1);
INSERT INTO tables (id, free) VALUES (2, 1);
INSERT INTO tables (id, free) VALUES (3, 0);
INSERT INTO tables (id, free) VALUES (4, 1);
INSERT INTO tables (id, free) VALUES (5, 0);

INSERT INTO menu_items (id, name) VALUES (1, 'Risotto');
INSERT INTO menu_items (id, name) VALUES (2, 'Salmon');
INSERT INTO menu_items (id, name) VALUES (3, 'Vodka');
INSERT INTO menu_items (id, name) VALUES (4, 'Pizza');
INSERT INTO menu_items (id, name) VALUES (5, 'Pork');

INSERT INTO stock (id, menu_item_id, count) VALUES (1, 1, 61);
INSERT INTO stock (id, menu_item_id, count) VALUES (2, 2, 100);
INSERT INTO stock (id, menu_item_id, count) VALUES (3, 3, 1);
INSERT INTO stock (id, menu_item_id, count) VALUES (4, 4, 0);
INSERT INTO stock (id, menu_item_id, count) VALUES (5, 5, 25);
