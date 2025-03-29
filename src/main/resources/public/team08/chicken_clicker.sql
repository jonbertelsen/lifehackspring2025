BEGIN;


CREATE TABLE IF NOT EXISTS public.lifehack_team_08_users
(
    user_id serial NOT NULL,
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    eggs bigint NOT NULL DEFAULT 0,
    chicken_feed_tier integer NOT NULL DEFAULT 0,
    predator_tier integer NOT NULL DEFAULT 0,
    CONSTRAINT lifehack_team_08_users_pkey PRIMARY KEY (user_id),
    CONSTRAINT lifehack_team_08_users_username_key UNIQUE (username)
    );
END;