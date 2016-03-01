-- US11 : on change le type de la colonne smartphones: set -> map

DROP TABLE users;

CREATE TABLE IF NOT EXISTS users (
   username text PRIMARY KEY,
   firstname text,
   lastname text,
   pass text,
   smartphones map<uuid, text>,
   addresses map<text, frozen<address>>
);