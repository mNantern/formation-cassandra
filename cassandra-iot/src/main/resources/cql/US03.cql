-- US03 : créer la table permettant de gérer les users

CREATE TYPE address (
  street text,
  city text,
  zip int
);

CREATE TABLE users (
   username text PRIMARY KEY,
   firstname text,
   lastname text,
   pass text,
   smartphones set<text>,
   addresses map<text, frozen<address>>
);