CREATE TABLE Price (
  id SERIAL PRIMARY KEY ,
  provider SERIAL REFERENCES Provider(id),
  value REAL,
  time TIMESTAMP
);

CREATE TABLE Provider (
  id SERIAL PRIMARY KEY,
  shortname VARCHAR(30),
  fullname VARCHAR(40),
  type SERIAL REFERENCES Providers_Type(id)
);

CREATE TABLE Provider_Type (
  id SERIAL PRIMARY KEY ,
  name VARCHAR(30)
);

INSERT INTO Provider_Type(name) VALUES ('Banco');
INSERT INTO Provider_Type(name) VALUES ('Casa de Cambio');
INSERT INTO Provider_Type(name) VALUES ('Gestora de cartao');

INSERT INTO Provider(shortname, fullname, type) VALUES ('BB', 'Banco do Brasil S.A.', '1');
INSERT INTO Provider(shortname, fullname, type) VALUES ('ITAU', 'Itau Unibanco S.A.', '1');
INSERT INTO Provider(shortname, fullname, type) VALUES ('CONFIDENCE', 'Confidence Corretora de Cambio S.A.', '2');
INSERT INTO Provider(shortname, fullname, type) VALUES ('NUBANK', 'Nu Pagamentos S.A.', '3');
