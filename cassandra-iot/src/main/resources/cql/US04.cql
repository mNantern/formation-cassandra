-- US03 : créer la table permettant de gérer les smartphones

CREATE TABLE smartphones (
   id uuid PRIMARY KEY,
   name text,
   constructor text,
   model text
);