# Exercice 02 : Premières requêtes

## Objectifs de l'exercice

Dans cet exercice nous allons:
* Créer notre premier keyspace et notre première table
* Remplir notre table à partir d'un fichier CSV
* Tester des requêtes sur notre nouvelle table

## Création du keyspace

Si ce n'est pas encore fait créer un keyspace nommé `cassandra_iot` pour nos futurs exercices.

La documentation pour la création d'un keyspace est disponible sur la page [CREATE KEYSPACE.](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/create_keyspace_r.html)

Puis sélectionner le keyspace via la [commande USE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/use_r.html).

## Création de notre première table: 'users'

Créer une table users contenant les données suivantes, **id** étant la clé primaire:

| Nom de la colonne     | Type de données     |
| :------------- | :------------- |
| id       | UUID       |
| first_name       | text       |
| last_name       | text       |
| email       | text       |

## Chargement de données

Nous allons maintenant charger quelques utilisateurs dans notre nouvelle table:

1. Regarder le contenu du fichier exercices/data/02_users.csv
2. Charger le fichier dans notre table `users` en utilisant la commande [COPY](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/copy_r.html)
3. Vérifier que le bon nombre de données ont été inséré

## Premières requêtes

Maintenant que nos données sont chargées dans notre table users nous allons effectuer quelques requêtes dessus:

1. Sélectionner l'ensemble des utilisateurs de la table
2. Sélectionner les 10 premiers utilisateurs
3. Sélectionner un utilisateur via son id
4. Sélectionner un utilisateur via son email

:interrobang::interrobang::interrobang::interrobang:

**=> Retour aux slides !**

## Les index secondaires

1. Ajouter un index secondaire sur la colonne email de notre table `users` en suivant la documentation sur [CREATE INDEX](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/create_index_r.html)
2. Refaire la requête de sélection d'un utilisateur par email.

Victoire ! Mais il ne faut pas utiliser d'index secondaires :astonished:

**=> Retour aux slides !**

## Création d'une table annexe

Nous allons créer une nouvelle table `users_by_email` nous permettant d'effectuer une requête sur nos utilisateurs en fonction de l'email qui sera alors notre clé primaire:

| Nom de la colonne     | Type de données     |
| :------------- | :------------- |
| email       | text       |
| id       | UUID       |
| first_name       | text       |
| last_name       | text       |

Charger les données dans cette nouvelle table.

Refaire alors la requête de sélection d'un utilisateur par email.
