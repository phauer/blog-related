CREATE TABLE designs (
  id int AUTO_INCREMENT PRIMARY KEY,
  title varchar(100) NOT NULL,
  imageUrl varchar(100) NOT NULL
);

INSERT INTO designs (title, imageUrl) VALUES ('Cat', 'http://domain.de/cat.jpg');
INSERT INTO designs (title, imageUrl) VALUES ('Dog', 'http://domain.de/dog.jpg');