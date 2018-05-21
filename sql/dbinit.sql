CREATE DATABASE msa
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Japanese_Japan.932'
    LC_CTYPE = 'Japanese_Japan.932'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA usermanagement
    AUTHORIZATION postgres;

-- DROP TABLE usermanagement.users;

CREATE TABLE usermanagement.users
(
    id integer NOT NULL DEFAULT nextval('usermanagement.users_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    address character varying COLLATE pg_catalog."default",
    phonenumber character varying COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE usermanagement.users
    OWNER to postgres;

INSERT INTO usermanagement.users ( name, address, phonenumber ) VALUES
 ('KeiNakamatsu','和歌山県田辺市どこか','09011003300')
 , ('EmiNakamatsu','和歌山県田辺市どっか','090888888')
 , ('OrenoTastuya','ここではないどこか','98309')
;
