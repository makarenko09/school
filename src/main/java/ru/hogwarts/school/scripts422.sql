-- SQL-script

CREATE TABLE car (
                     id SERIAL PRIMARY KEY,
                     brand TEXT NOT NULL,
                     model TEXT NOT NULL,
                     price INT NOT NULL
);

CREATE TABLE driver (
                        id SERIAL PRIMARY KEY,
                        name TEXT NOT NULL,
                        age INT NOT NULL,
                        rules BOOLEAN DEFAULT FALSE
);

-- SQL-console
ALTER TABLE driver ADD COLUMN car_id INT;
ALTER TABLE driver ADD CONSTRAINT fk_car FOREIGN KEY (car_id) REFERENCES car(id);