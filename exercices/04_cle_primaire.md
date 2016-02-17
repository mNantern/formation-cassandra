# Exercice 04 : La clé primaire

## Commencer par les requêtes

Mon client vient d'exprimer une demande pour mon application cassandra-iot:

> Je veux pouvoir récupérer les données remontées par un objet connecté

Modéliser la table `data` destinée à stocker les données d'un objet connecté sachant qu'une donnée est composée d'une date et d'une valeur.

## Utiliser une colonne de clustering

Réaliser une représentation naïve de la table `data` ne permet pas de répondre à la demande du client qui est de pouvoir interroger cette table en fonction de l'id de notre objet connecté.

Proposer une modélisation utilisant une [colonne de clustering](https://docs.datastax.com/en/cql/3.3/cql/cql_using/useCompoundPrimaryKey.html)

:information_source: La documentation pour [supprimer une table](https://docs.datastax.com/en/cql/3.3/cql/cql_reference/drop_table_r.html)

**=> Retour aux slides !**

## Range request

Importer les données du fichier TODO
Récupérer l'ensemble des données pour le capteur TODO situé entre les dates TODO et TODO.

## Récupérer les données de la plus récente à la plus ancienne

Le client précise son besoin, il souhaite maintenant récupérer les données des capteurs dans un ordre décroissant à savoir la donnée la plus récente doit être récupérée en première.

Utilisez pour cela un ["clustering order"](https://docs.datastax.com/en/cql/3.3/cql/cql_reference/refClstrOrdr.html)

:information_source: Il est également possible d'[ordonner le résultat d'un SELECT](https://docs.datastax.com/en/cql/3.3/cql/cql_reference/select_r.html?scroll=reference_ds_d35_v2q_xj__using-compound-primary-keys-and-sorting-results) mais il est plus efficace de le déclarer une fois pour toute la table qu'à chaque requête.

**=> Retour aux slides !**

## Limiter la taille de la partition
