# Solution Exercice 02 : Premières requêtes

Solution de l'exercice 02

## Création du keyspace

```SQL
CREATE KEYSPACE cassandra-iot
WITH REPLICATION = {
	'class' : 'SimpleStrategy',
	'replication_factor' : 1
};
```

Sélectionner le keyspace:

```SQL
USE cassandra-iot;
```

## Création de notre première table: 'users'

```SQL
CREATE TABLE users (
	user_id UUID PRIMARY KEY,
	first_name TEXT,
	last_name TEXT,
	email TEXT
);
```

## Chargement de données

Pour charger le fichier CQL il faut exécuter la commande suivante:

```
COPY users FROM '02_users.csv' WITH HEADER=true;
```

## Premières requêtes

```SQL
SELECT * from users;
SELECT * from users LIMIT 10;
SELECT * from users WHERE id=;
```
