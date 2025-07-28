CREATE TABLE telegram_user_state (
  id UUID NOT NULL,
   user_status VARCHAR(255),
   activity JSONB,
   CONSTRAINT pk_telegram_user_state PRIMARY KEY (id)
);

ALTER TABLE telegram_user_state ADD CONSTRAINT FK_TELEGRAM_USER_STATE_ON_ID FOREIGN KEY (id) REFERENCES telegram_user (id);