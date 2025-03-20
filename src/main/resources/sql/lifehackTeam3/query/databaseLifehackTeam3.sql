--
-- PostgreSQL database dump
--
-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.4

-- Started on 2025-03-20 14:37:17 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 32832)
-- Name: reminder_subscriber_lifehack_team_3; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reminder_subscriber_lifehack_team_3 (
                                                            reminder_id integer NOT NULL,
                                                            mail_id integer NOT NULL
);


ALTER TABLE public.reminder_subscriber_lifehack_team_3 OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 32845)
-- Name: reminders_description_lifehack_team_3; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reminders_description_lifehack_team_3 (
                                                              description_id integer NOT NULL,
                                                              reminder_id integer NOT NULL,
                                                              description text NOT NULL
);


ALTER TABLE public.reminders_description_lifehack_team_3 OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 32844)
-- Name: reminders_description_description_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reminders_description_description_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reminders_description_description_id_seq OWNER TO postgres;

--
-- TOC entry 3388 (class 0 OID 0)
-- Dependencies: 218
-- Name: reminders_description_description_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reminders_description_description_id_seq OWNED BY public.reminders_description_lifehack_team_3.description_id;


--
-- TOC entry 217 (class 1259 OID 32838)
-- Name: reminders_lifehack_team_3; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reminders_lifehack_team_3 (
                                                  reminder_id integer NOT NULL,
                                                  reminder_name character varying(100) NOT NULL
);


ALTER TABLE public.reminders_lifehack_team_3 OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 32837)
-- Name: reminders_reminder_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reminders_reminder_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reminders_reminder_id_seq OWNER TO postgres;

--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 216
-- Name: reminders_reminder_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reminders_reminder_id_seq OWNED BY public.reminders_lifehack_team_3.reminder_id;


--
-- TOC entry 221 (class 1259 OID 32854)
-- Name: users_lifehack_team_3; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_lifehack_team_3 (
                                              user_id bigint NOT NULL,
                                              user_name character varying NOT NULL,
                                              user_password character varying NOT NULL,
                                              user_email character varying NOT NULL
);


ALTER TABLE public.users_lifehack_team_3 OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 32853)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users_lifehack_team_3.user_id;


--
-- TOC entry 3218 (class 2604 OID 32848)
-- Name: reminders_description_lifehack_team_3 description_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminders_description_lifehack_team_3 ALTER COLUMN description_id SET DEFAULT nextval('public.reminders_description_description_id_seq'::regclass);


--
-- TOC entry 3217 (class 2604 OID 32841)
-- Name: reminders_lifehack_team_3 reminder_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminders_lifehack_team_3 ALTER COLUMN reminder_id SET DEFAULT nextval('public.reminders_reminder_id_seq'::regclass);


--
-- TOC entry 3219 (class 2604 OID 32857)
-- Name: users_lifehack_team_3 user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_lifehack_team_3 ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3376 (class 0 OID 32832)
-- Dependencies: 215
-- Data for Name: reminder_subscriber_lifehack_team_3; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reminder_subscriber_lifehack_team_3 (reminder_id, mail_id) FROM stdin;
3	21
1	21
2	21
1	24
\.


--
-- TOC entry 3380 (class 0 OID 32845)
-- Dependencies: 219
-- Data for Name: reminders_description_lifehack_team_3; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reminders_description_lifehack_team_3 (description_id, reminder_id, description) FROM stdin;
16	1	Tag en skive, din bandit!
17	1	Har du husket at tage minimum 5 skiver i dag, kammerat?
18	1	Tjek lige, om du har en snus i overlÃ¦ben!
19	1	Skive-time: SÃ¦t en i kÃ¦ften!
20	1	Skal du lige have en ekstra skive at hygge med?
21	2	Har du tÃ¦ndt en smÃ¸g i dag? TÃ¦nk dig om!
22	2	Hvor meget har du rÃ¸get i dag, dit klamme svin?
23	2	Tiden er inde til en rygepause â€“ en pause fra rygning?
24	2	Du er en taber hvis du ikke kan holde vejret i 2 minutter
25	2	Har du overvejet at skÃ¦re ned pÃ¥ smÃ¸gerne i dag?
26	3	Har du husket dagens trÃ¦ning?
27	3	FÃ¥ sved pÃ¥ panden â€“ gÃ¥ i gang nu!
28	3	En lille lÃ¸betur skader aldrig!
29	3	Klar til at pumpe jern i dag?
30	3	5 push-ups nu, dit fede svin!
\.


--
-- TOC entry 3378 (class 0 OID 32838)
-- Dependencies: 217
-- Data for Name: reminders_lifehack_team_3; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reminders_lifehack_team_3 (reminder_id, reminder_name) FROM stdin;
1	skive_reminder
2	rygning_reminder
3	traening_reminder
\.


--
-- TOC entry 3382 (class 0 OID 32854)
-- Dependencies: 221
-- Data for Name: users_lifehack_team_3; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_lifehack_team_3 (user_id, user_name, user_password, user_email) FROM stdin;
21	MarcusPFF	1234	MarcusPFF@bingbong.com
22	bob	1234	bobbymarley@afrimail.com
23	bobob	1234	1234567@suhfzxc.com
24	Faster_Jonas	1234	jonas.outzen.j@gmail.com
\.


--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 218
-- Name: reminders_description_description_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reminders_description_description_id_seq', 30, true);


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 216
-- Name: reminders_reminder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reminders_reminder_id_seq', 6, true);


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 24, true);


--
-- TOC entry 3221 (class 2606 OID 32836)
-- Name: reminder_subscriber_lifehack_team_3 reminder_subscriber_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminder_subscriber_lifehack_team_3
    ADD CONSTRAINT reminder_subscriber_pkey PRIMARY KEY (reminder_id, mail_id);


--
-- TOC entry 3225 (class 2606 OID 32852)
-- Name: reminders_description_lifehack_team_3 reminders_description_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminders_description_lifehack_team_3
    ADD CONSTRAINT reminders_description_pkey PRIMARY KEY (description_id);


--
-- TOC entry 3223 (class 2606 OID 32843)
-- Name: reminders_lifehack_team_3 reminders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminders_lifehack_team_3
    ADD CONSTRAINT reminders_pkey PRIMARY KEY (reminder_id);


--
-- TOC entry 3227 (class 2606 OID 32882)
-- Name: users_lifehack_team_3 unique_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_lifehack_team_3
    ADD CONSTRAINT unique_email UNIQUE (user_email);


--
-- TOC entry 3229 (class 2606 OID 32861)
-- Name: users_lifehack_team_3 users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_lifehack_team_3
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3232 (class 2606 OID 32872)
-- Name: reminders_description_lifehack_team_3 fk_reminder; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminders_description_lifehack_team_3
    ADD CONSTRAINT fk_reminder FOREIGN KEY (reminder_id) REFERENCES public.reminders_lifehack_team_3(reminder_id) ON DELETE CASCADE;


--
-- TOC entry 3230 (class 2606 OID 32883)
-- Name: reminder_subscriber_lifehack_team_3 fk_subscriber_mail; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminder_subscriber_lifehack_team_3
    ADD CONSTRAINT fk_subscriber_mail FOREIGN KEY (mail_id) REFERENCES public.users_lifehack_team_3(user_id) ON DELETE CASCADE;


--
-- TOC entry 3231 (class 2606 OID 32867)
-- Name: reminder_subscriber_lifehack_team_3 fk_subscriber_reminder; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reminder_subscriber_lifehack_team_3
    ADD CONSTRAINT fk_subscriber_reminder FOREIGN KEY (reminder_id) REFERENCES public.reminders_lifehack_team_3(reminder_id) ON DELETE CASCADE;


-- Completed on 2025-03-20 14:37:17 UTC

--
-- PostgreSQL database dump complete
--

