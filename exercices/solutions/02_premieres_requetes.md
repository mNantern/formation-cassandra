# Solution Exercice 02 : Premières requêtes

Solution de l'exercice 02

## Création du keyspace

```sql
CREATE KEYSPACE cassandra-iot
WITH REPLICATION = {
	'class' : 'SimpleStrategy',
	'replication_factor' : 1
};
```

Sélectionner le keyspace:

```sql
USE cassandra-iot;
```

## Création de notre première table: 'users'

```sql
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

```sql
SELECT * from users;
SELECT * from users LIMIT 10;
SELECT * from users WHERE id=89cf44a7-50df-49e6-80a1-183259d4121b;
SELECT * from users WHERE email='rbailey5@ycombinator.com';
```

## Les index secondaires

```sql
CREATE INDEX ON users (email);
SELECT * from users WHERE email='rbailey5@ycombinator.com';
```

## Création d'une table annexe

```sql
CREATE TABLE users_by_email (
	email TEXT PRIMARY KEY,
	user_id UUID,
	first_name TEXT,
	last_name TEXT
);

SELECT * from users_by_email WHERE email='rbailey5@ycombinator.com';
```
