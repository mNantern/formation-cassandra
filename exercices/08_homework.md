# Exercice 08 : Homework

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

## Data

### US18 : Partitionner les données

Si les smartphones continuent de remonter tellement d'informations la limite raisonnable de 1 à 2 millions d'éléments par ligne sera vite atteinte.

Il est nécessaire de partitionner les données des smartphones à la journée tout en conservant la recherche sur une plage de date et la pagination.

Modifier la clé de partitionnement pour que celle-ci soit composée de l'identifiant du smartphone + de la date du jour.
