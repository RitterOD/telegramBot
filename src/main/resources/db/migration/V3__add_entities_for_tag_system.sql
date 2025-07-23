CREATE TABLE tag_to_tag_link (
  id UUID NOT NULL,
   row_created TIMESTAMP WITHOUT TIME ZONE,
   row_updated TIMESTAMP WITHOUT TIME ZONE,
   parent_tag_id UUID,
   children_tag_id UUID,
   CONSTRAINT pk_tag_to_tag_link PRIMARY KEY (id)
);

CREATE TABLE translation_tag (
  id UUID NOT NULL,
   row_created TIMESTAMP WITHOUT TIME ZONE,
   row_updated TIMESTAMP WITHOUT TIME ZONE,
   name VARCHAR(255),
   description TEXT,
   CONSTRAINT pk_translation_tag PRIMARY KEY (id)
);

CREATE TABLE translation_to_tag_link (
  id UUID NOT NULL,
   row_created TIMESTAMP WITHOUT TIME ZONE,
   row_updated TIMESTAMP WITHOUT TIME ZONE,
   translation_id UUID,
   tag_id UUID,
   CONSTRAINT pk_translation_to_tag_link PRIMARY KEY (id)
);

ALTER TABLE word ADD CONSTRAINT UC_WORD_WORD UNIQUE (word);

ALTER TABLE tag_to_tag_link ADD CONSTRAINT FK_TAG_TO_TAG_LINK_ON_CHILDREN_TAG FOREIGN KEY (children_tag_id) REFERENCES translation_tag (id);

ALTER TABLE tag_to_tag_link ADD CONSTRAINT FK_TAG_TO_TAG_LINK_ON_PARENT_TAG FOREIGN KEY (parent_tag_id) REFERENCES translation_tag (id);

ALTER TABLE translation_to_tag_link ADD CONSTRAINT FK_TRANSLATION_TO_TAG_LINK_ON_TAG FOREIGN KEY (tag_id) REFERENCES translation_tag (id);

ALTER TABLE translation_to_tag_link ADD CONSTRAINT FK_TRANSLATION_TO_TAG_LINK_ON_TRANSLATION FOREIGN KEY (translation_id) REFERENCES word (id);

ALTER TABLE word ALTER COLUMN  translation SET NOT NULL;
