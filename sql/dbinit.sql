CREATE DATABASE msa;
 
CREATE TABLE usermanagement.users (
id SERIAL PRIMARY KEY
, name VARCHAR
, address VARCHAR
, phonenumber VARCHAR
);
 
INSERT INTO usermanagement.users ( name, address, phonenumber ) VALUES
 ('KeiNakamatsu','和歌山県田辺市どこか','09011003300')
 , ('EmiNakamatsu','和歌山県田辺市どっか','090888888')
 , ('OrenoTastuya','ここではないどこか','98309')
;
