# Exercice 03 : Insérer des données

Toujours sur notre table `users` nous allons tenter d'insérer des données.

## La méthode INSERT

1. Insérer un nouvel utilisateur: cf la [documentation de la méthode INSERT](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/insert_r.html)
2. Réaliser un INSERT sur un utilisateur existant puis l'afficher

## La méthode UPDATE

Refaire le même test avec la méthode [UPDATE](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/update_r.html) en commençant par mettre à jour un utilisateur déjà existant puis en refaisant le mêm test sur un utilisateur n'existant pas dans notre table.

## LightWeight Transaction (LWT)

Essayer de créer un utilisateur existant déjà en utilisant `IF NOT EXISTS`
