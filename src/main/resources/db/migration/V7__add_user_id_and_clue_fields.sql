ALTER TABLE word ADD from_translate_clue VARCHAR(255);

ALTER TABLE word ADD technical_user_id UUID;

ALTER TABLE word ADD telegram_user_id UUID;

ALTER TABLE word ADD to_translate_clue VARCHAR(255);

ALTER TABLE word DROP CONSTRAINT uc_word_word;