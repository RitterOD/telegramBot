ALTER TABLE word DROP COLUMN  telegram_user_id;
ALTER TABLE word ADD COLUMN telegram_user_id bigint;
UPDATE word set telegram_user_id = 263690572