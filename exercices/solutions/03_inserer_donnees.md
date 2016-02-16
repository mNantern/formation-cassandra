# Solution Exercice 03 : Insérer des données

## La méthode INSERT

```sql
INSERT INTO users (id,first_name,last_name,email) VALUES (uuid(),'John','Doe','jdoe@acme.org');
INSERT INTO users (id,first_name,last_name,email) VALUES (09958a2f-87e6-43bf-9702-225425eb0405,'Catherine','Elliott','celliott0@capiture.com');
```

## La méthode UPDATE

Sur un utilisateur existant:
```sql
UPDATE users
  SET email = 'celliott0@omniture.com'
  WHERE id = 09958a2f-87e6-43bf-9702-225425eb0405;
```

Sur un nouvel utilisateur:
```sql
UPDATE users
  SET first_name = 'John',
  last_name = 'Smith',
  email = 'jsmith@cassie.com'
  WHERE id = uuid();
```

## LightWeight Transaction (LWT)

```sql
INSERT INTO users (id,first_name,last_name,email) VALUES (09958a2f-87e6-43bf-9702-225425eb0405,'Catherine','Elliott','celliott0@capiture.com') IF NOT EXISTS;
```
