CREATE DATABASE IF NOT EXISTS springdata_demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE springdata_demo;

-- 1️⃣ LANGUAGE
CREATE TABLE Language (
    LanguageID CHAR(2) PRIMARY KEY,
    Language VARCHAR(20) NOT NULL
);

INSERT INTO Language (LanguageID, Language)
VALUES ('EN', 'English'),
       ('VI', 'Vietnamese');

-- 2️⃣ PRODUCT CATEGORY
CREATE TABLE ProductCategory (
    ProductCategoryID INT AUTO_INCREMENT PRIMARY KEY,
    CanBeShipped BIT
);

INSERT INTO ProductCategory (CanBeShipped)
VALUES (1), (0);

-- 3️⃣ PRODUCT CATEGORY TRANSLATION
CREATE TABLE ProductCategoryTranslation (
    ProductCategoryID INT,
    LanguageID CHAR(2),
    CategoryName VARCHAR(100),
    PRIMARY KEY (ProductCategoryID, LanguageID),
    FOREIGN KEY (ProductCategoryID) REFERENCES ProductCategory(ProductCategoryID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (LanguageID) REFERENCES Language(LanguageID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO ProductCategoryTranslation
(ProductCategoryID, LanguageID, CategoryName)
VALUES
(1, 'EN', 'Electronics'),
(1, 'VI', 'Điện tử'),
(2, 'EN', 'Furniture'),
(2, 'VI', 'Nội thất');

-- 4️⃣ PRODUCT
CREATE TABLE Product (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Price DECIMAL(10,2),
    Weight DECIMAL(4,2),
    ProductCategoryID INT,
    FOREIGN KEY (ProductCategoryID) REFERENCES ProductCategory(ProductCategoryID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Product (Price, Weight, ProductCategoryID)
VALUES (19.99, 0.50, 1),
       (59.99, 2.20, 2);

-- 5️⃣ PRODUCT TRANSLATION
CREATE TABLE ProductTranslation (
    ProductID INT,
    LanguageID CHAR(2),
    ProductName VARCHAR(100),
    ProductDescription VARCHAR(100),
    PRIMARY KEY (ProductID, LanguageID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (LanguageID) REFERENCES Language(LanguageID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO ProductTranslation
(ProductID, LanguageID, ProductName, ProductDescription)
VALUES
(1, 'EN', 'Wireless Mouse', 'A smooth optical wireless mouse'),
(1, 'VI', 'Chuột không dây', 'Chuột quang không dây mượt mà'),
(2, 'EN', 'Wooden Chair', 'A sturdy wooden chair'),
(2, 'VI', 'Ghế gỗ', 'Ghế gỗ chắc chắn');
