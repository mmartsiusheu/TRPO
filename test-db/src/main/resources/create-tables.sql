DROP TABLE IF EXISTS category;

CREATE TABLE category (
  category_id INT NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(255) NOT NULL UNIQUE,
  parent_id INT,
  PRIMARY KEY (category_id),
  FOREIGN KEY (parent_id) REFERENCES category(category_id)
);

DROP TABLE IF EXISTS product;

CREATE TABLE product (
  prod_id INT NOT NULL AUTO_INCREMENT,
  prod_name VARCHAR (255) NOT NULL UNIQUE,
  prod_amount INT NOT NULL ,
  date_added DATE NOT NULL,
  category_id INT NOT NULL,
  PRIMARY KEY (prod_id, category_id),
  FOREIGN KEY (category_id) REFERENCES category(category_id)
);
