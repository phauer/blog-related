CREATE TABLE designs (
  id int AUTO_INCREMENT PRIMARY KEY,
  title varchar(100) NOT NULL,
  imageUrl varchar(100) NOT NULL,
  dateModified TIMESTAMP NOT NULL
);