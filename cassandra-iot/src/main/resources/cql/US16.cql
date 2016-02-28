-- US16 : ajout d'une table permettant de compter le nombre de données par smartphone

CREATE TABLE number_data_by_smartphones (
  smartphone_id UUID PRIMARY KEY,
  data counter
);