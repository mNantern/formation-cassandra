# Solution Exercice 04 : La clé primaire

## Commencer par les requêtes

Une modélisation naïve:

```sql
CREATE TABLE data (
	id UUID PRIMARY KEY,
	smartphone_id UUID,
	type TEXT,
	event_time timestamp,
  value TEXT
);
```
## Utiliser une colonne de clustering

```sql
CREATE TABLE data (
	id UUID,
	smartphone_id UUID,
	type TEXT,
	event_time timestamp,
  value TEXT,
  PRIMARY KEY (smartphone_id,id)
);
```

## Range request

```sql
CREATE TABLE data (
	id UUID,
	smartphone_id UUID,
	type TEXT,
	event_time timestamp,
  value TEXT,
  PRIMARY KEY (smartphone_id,event_time,id)
);
```

## Récupérer les données de la plus récente à la plus ancienne

```sql
CREATE TABLE data (
	id UUID,
	smartphone_id UUID,
	type TEXT,
	event_time timestamp,
  value TEXT,
  PRIMARY KEY (smartphone_id, event_time, id)
) WITH CLUSTERING ORDER BY (event_time DESC);
```

## Limiter la taille de la partition

```sql
CREATE TABLE data (
	id UUID,
	smartphone_id UUID,
	type TEXT,
	event_time timestamp,
  value TEXT,
  event_date date,
  PRIMARY KEY ((smartphone_id,event_date), event_time, id)
) WITH CLUSTERING ORDER BY (event_time DESC);
```
