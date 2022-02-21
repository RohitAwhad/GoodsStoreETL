CREATE TYPE public.item_department AS ENUM ('BIKE', 'CAMPING', 'SKATEBOARDING');
CREATE SCHEMA IF NOT EXISTS staging;
CREATE TABLE public.item
(
    item_id    SERIAL
        CONSTRAINT item_pk
            PRIMARY KEY,
    name       VARCHAR    NOT NULL,
    price      FLOAT      NOT NULL,
    department public.item_department NOT NULL
);


CREATE TABLE staging.orders
(
    created timestamp,
    customer VARCHAR,
    item_id    integer,
    name       VARCHAR,
    price      FLOAT,
    department VARCHAR 
);

INSERT INTO public.item (item_id, name, price, department) VALUES (1, 'Favorit Aviator', 2500, 'BIKE');
INSERT INTO public.item (item_id, name, price, department) VALUES (2, 'Favorit Cronos ', 2200, 'BIKE');
INSERT INTO public.item (item_id, name, price, department) VALUES (3, 'Ghost Lector Advanced', 4000, 'BIKE');
INSERT INTO public.item (item_id, name, price, department) VALUES (4, 'Husky Bonelli 3', 1200, 'CAMPING');
INSERT INTO public.item (item_id, name, price, department) VALUES (5, 'Coleman BlackOut 4', 1300, 'CAMPING');
INSERT INTO public.item (item_id, name, price, department) VALUES (6, 'Toy Machine Monster Board', 108, 'SKATEBOARDING');
INSERT INTO public.item (item_id, name, price, department) VALUES (7, 'Element Selection Board', 104, 'SKATEBOARDING');

