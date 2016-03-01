# Exercice 05 : Sprint 1

## Connexion à Cassandra

### US00 : créer une session vers Cassandra
La première étape va être de se connecter à Cassandra. Il va falloir créer une session puis créer un keyspace `cassandra_iot` si celui-ci n'existe pas.

Voici la documentation associée et le code doit être mis en place dans la classe Application:
* [Se connecter à un cluster Cassandra](http://docs.datastax.com/en/developer/java-driver/3.0/java-driver/quick_start/qsSimpleClientCreate_t.html)
* [Créer une session](http://docs.datastax.com/en/developer/java-driver/3.0/java-driver/quick_start/qsSimpleClientAddSession_t.html)
* [Créer un keyspace](http://docs.datastax.com/en/cql/3.3/cql/cql_using/useExampleCreatingKeyspace.html) en utilisant la topologie `NetworkTopologyStrategy` et un RF de 1

## Data

On souhaite afficher les données d'**un smartphone**:
![Smartphone Data](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/smartphone_data1.png)

### US01 : Stockage des données

La première étape consiste à modéliser notre table `data`. Voici les données envoyées par le smartphone:

| Donnée     | Exemple     |
| :------------- | :------------- |
| smartphoneId       | f98e9b49-8911-4e87-87f8-723588c2b09e       |
| type       | BRIGHTNESS       |
| eventTime       | 2016-02-19T17:12:41Z       |
| value       | 29.0      |

Une fois la table modélisée et créée dans Cassandra il est nécessaire de coder le service associé au controller `POST /data`.

Il existe trois façons d'exécuter des requêtes dans Cassandra avec le driver:

1. Simple statement : la plus simple, nous l'avons utilisé pour créer notre keyspace.
2. [Query Builder](https://docs.datastax.com/en/developer/java-driver/3.0/java-driver/reference/queryBuilder_r.html): permet de construire une requête CQL via une fluent API
3. [Prepared statement](http://docs.datastax.com/en/developer/java-driver/3.0/java-driver/quick_start/qsSimpleClientBoundStatements_t.html) : la méthode la plus efficace et la plus sûre. Il ne faut pas préparer une requête deux fois !

:warning: La méthode "Simple statement" est vulnérable aux injections CQL. A éviter !

:information_source:  pour le POST nous allons utiliser une PreparedStatement.

### US02 : Lecture des données

Afin de créer la page exposée ci-dessus il faut pouvoir renvoyer la liste des données associées à un smartphoneId.

:information_source:  pour le GET nous allons utiliser un QueryBuilder.

## Utilisateur

On souhaite pouvoir afficher, modifier et supprimer les données d'**un utilisateur**:

![User Data](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/user1.png)

### US03: gestion d'un utilisateur

1. Modélisez la table `users` en fonction des données nécessaires
2. Coder les 4 méthodes de base permettant de gérer un utilisateur (Create, Read, Update, Delete)

:information_source: pour insérer un UDT se reporter à [la documentation](https://docs.datastax.com/en/developer/java-driver/3.0/java-driver/reference/udtApi.html).

## Smartphone

![Smartphone Details](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/smartphone_details.png)

### US04: gestion des smartphones

1. Modélisation de la table `smartphones`
2. Coder les méthodes CRUD pour la ressource smartphone en utilisant l'[Object Mapper](https://docs.datastax.com/en/latest-java-driver/java-driver/reference/crudOperations.html) du driver.
