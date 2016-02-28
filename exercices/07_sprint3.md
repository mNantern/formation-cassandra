# Exercice 07 : Sprint 3

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

En utilisant une table `number_data_by_devices` avec une donnée de type `COUNTER` renvoyer dans notre service développé lors de l'US14 le nombre de données par smartphone.

## Utilisateur

![User detais v3](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/user3.png)

### US17 : Supprimer un smartphone

Il faut pouvoir donner la possibilité à l'utilisateur de supprimer un de ses smartphones.
A la suppression du smartphone il est nécessaire de:
* Supprimer l'objet smartphone
* Mettre à jour la ressource utilisateur pour enlever le smartphone venant d'être supprimé
* Supprimer les données associées au smartphone dans la table `data`

Il existe encore une fois deux possibilités pour réaliser cela:
* Utiliser la [requête BATCH](http://docs.datastax.com/en/developer/java-driver/3.0/java-driver/reference/batch-statements.html)
* Effectuer des [requêtes asynchrones](https://lostechies.com/ryansvihla/2014/08/28/cassandra-batch-loading-without-the-batch-keyword/)
