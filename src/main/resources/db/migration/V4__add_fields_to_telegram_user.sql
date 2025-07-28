ALTER TABLE telegram_user ADD first_name VARCHAR(255);

ALTER TABLE telegram_user ADD lang_code VARCHAR(255);

ALTER TABLE telegram_user ADD CONSTRAINT UC_TELEGRAM_USER_TELEGRAM UNIQUE (telegram_id);