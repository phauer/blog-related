CREATE TABLE designs (
  id           INT AUTO_INCREMENT PRIMARY KEY,
  title        VARCHAR(100) NOT NULL,
  imageUrl     VARCHAR(100) NOT NULL,
  dateModified TIMESTAMP(0) NOT NULL
);