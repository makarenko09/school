ALTER TABLE student add CONSTRAINT age_constraint CHECK (age >= 16);
ALTER TABLE student add CONSTRAINT name_unique unique (name);
ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;
ALTER TABLE student ALTER COLUMN name SET NOT NULL;
ALTER TABLE faculty add CONSTRAINT name_color_unique unique (name,color);