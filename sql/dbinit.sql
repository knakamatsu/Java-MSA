CREATE DATABASE sbdb;
 
CREATE TABLE counter1 (
id SERIAL PRIMARY KEY
, title VARCHAR(16)
, count INT);
 
INSERT INTO counter1 VALUES
 (0,'all',0)
 , (1,'select',0)
 , (2,'update',0)
 , (3,'delete',0);
 