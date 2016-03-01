# Exercice 07 : Sprint 3

## Users

### US11 : Remplacer l'identifiant du smartphone par son nom

**=> Retour aux slides !**

Dans notre cas nous allons considérer que le nom du smartphone ne change pas. Il faut donc dénormaliser les données en ajoutant également le nom du smartphone dans la ressource utilisateur.

## Data

![Smartphone Data v3](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/smartphone_data3.png)

### US12 : Paginer les données

Nos smartphones remontant des données toutes les secondes, la page web met beaucoup de temps à se charger. Il faudrait limiter le nombre de données par page à 20 et offrir dans notre réponse le champ `pagingState`.

Si le client a besoin de la page suivante il pourra l'obtenir en ajoutant ce champ en paramètre de l'url.

Afin de paginer les données nous allons utiliser un [PagingState](http://docs.datastax.com/en/drivers/java/3.0/index.html)

### US13 : Expirer les données

Afin de se mettre en conformité avec la CNIL nous devons expirer les données insérées au bout de 3 mois.
Nous avons pour cela deux possibilités:

1. Expirer les données en [modifiant la table `data`](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/alter_table_r.html) pour assigner une valeur à la propriété [default_time_to_live](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/tabProp.html)
2. [Insérer un TTL](http://docs.datastax.com/en/cql/3.3/cql/cql_reference/insert_r.html?scroll=refInsert__timestamp_ttl) à chaque insertion de données en base

L'option 1 est la plus sûre et la plus rapide néanmoins pour les besoins de l'exercice nous allons mettre en place la solution 2.

## Smartphone Admin

Afin de gérer la flotte de smartphone utilisant notre plateforme nous allons avoir besoin de créer une page listant l'ensemble des données de notre smartphone:

![Smartphone Admin](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/smartphone_admin.png)

### US14 : Lister tous les smartphones de manière paginée

Pour réaliser cet écran il faut renvoyer de manière paginée les données associées aux smartphones.

### US15 : Pouvoir effectuer une recherche par nom de constructeur

Nous voulons pouvoir filtrer notre liste de résultat en fonction du nom constructeur.

### US16 : Aficher le nombre de données par smartphone

En utilisant une table `number_data_by_smartphones` avec une donnée de type `COUNTER` renvoyez dans notre service développé lors de l'US14 le nombre de données par smartphone.
