CREATE TABLE feedback (
                          id serial primary key,
                          name VARCHAR(36) NOT NULL,
                          position VARCHAR(100) NOT NULL,
                          email VARCHAR (36) NOT NULL,
                          duration_in_months    INTEGER NOT NULL,
                          text VARCHAR(500) NOT NULL
);
