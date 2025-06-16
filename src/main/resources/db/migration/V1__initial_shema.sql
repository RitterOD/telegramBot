CREATE
telegram_user
(
    id uuid NOT NULL,
    telegram_id bigint NOT NULL,
    telegram_user_name text COLLATE pg_catalog."default",
    CONSTRAINT telegram_user_pkey PRIMARY KEY (id)
);


CREATE word
(
    id uuid NOT NULL,
    from_lang character varying(255),
    translation character varying(255),
    translation_transcriprion character varying(255),
    to_lang character varying(255),
    word character varying(255)  NOT NULL,
    word_transcription character varying(255),
    CONSTRAINT word_pkey PRIMARY KEY (id),
    CONSTRAINT word_from_lang_check CHECK (from_lang::text = ANY (ARRAY['RU'::character varying, 'ENG'::character varying]::text[])),
    CONSTRAINT word_to_lang_check CHECK (to_lang::text = ANY (ARRAY['RU'::character varying, 'ENG'::character varying]::text[]))
)
