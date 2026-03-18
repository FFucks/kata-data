CREATE TABLE sales_source (
    id SERIAL PRIMARY KEY,
    city VARCHAR(50),
    salesman VARCHAR(50),
    amount DECIMAL
);

INSERT INTO sales_source (city, salesman, amount) VALUES
('SP', 'Joao', 100),
('RJ', 'Maria', 200),
('MG', 'Pedro', 300),
('SP', 'Ana', 400);