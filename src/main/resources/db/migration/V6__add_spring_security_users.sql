CREATE TABLE authority (
  id UUID NOT NULL,
   authority VARCHAR(50),
   CONSTRAINT pk_authority PRIMARY KEY (id)
);

CREATE TABLE tech_user (
  id UUID NOT NULL,
   username VARCHAR(50),
   password VARCHAR(500),
   enabled BOOLEAN,
   CONSTRAINT pk_tech_user PRIMARY KEY (id)
);

CREATE TABLE tech_user_authorities (
  authorities_id UUID NOT NULL,
   user_id UUID NOT NULL,
   CONSTRAINT pk_tech_user_authorities PRIMARY KEY (authorities_id, user_id)
);

ALTER TABLE authority ADD CONSTRAINT UC_AUTHORITY_AUTHORITY UNIQUE (authority);

ALTER TABLE tech_user ADD CONSTRAINT UC_TECH_USER_USERNAME UNIQUE (username);

CREATE INDEX idx_securityuser_username ON tech_user(username);

ALTER TABLE tech_user_authorities ADD CONSTRAINT FK_TECUSEAUT_ON_AUTHORITY FOREIGN KEY (authorities_id) REFERENCES authority (id);

ALTER TABLE tech_user_authorities ADD CONSTRAINT FK_TECUSEAUT_ON_USER FOREIGN KEY (user_id) REFERENCES tech_user (id);