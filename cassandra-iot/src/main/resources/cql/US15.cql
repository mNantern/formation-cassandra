-- US15 : recherche de smartphone par nom constructeur

CREATE TABLE smartphones_by_constructor (
   id uuid,
   name text,
   constructor text,
   model text,
   owner text,
   PRIMARY KEY ((constructor), id)
);