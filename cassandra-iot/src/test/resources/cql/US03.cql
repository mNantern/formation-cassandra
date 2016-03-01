-- US03 : créer la table permettant de gérer les users

CREATE TYPE IF NOT EXISTS address (
  street text,
  city text,
  zip int
);

CREATE TABLE IF NOT EXISTS users (
   username text PRIMARY KEY,
   firstname text,
   lastname text,
   pass text,
   smartphones set<uuid>,
   addresses map<text, frozen<address>>
);