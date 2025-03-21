BEGIN;

CREATE TABLE IF NOT EXISTS public.category
(
    id serial,
    categoryname character varying NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.excuse
(
    id serial,
    excusetext character varying NOT NULL,
    sillyness_level integer NOT NULL,
    category integer NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY (category)
    REFERENCES public.category (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

END;