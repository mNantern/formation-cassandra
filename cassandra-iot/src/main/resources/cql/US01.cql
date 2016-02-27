-- US01 : créer la table permettant de gérer les data de nos smartphones

CREATE TABLE data (
   id uuid,
   smartphone_id uuid,
   event_time timestamp,
   type text,
   value text,
   PRIMARY KEY ((smartphone_id),event_time, type)
);