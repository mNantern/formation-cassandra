# Exercice 06 : Sprint 2

## Connexion à Cassandra

### US5 : Appeler le datacenter local

Il est possible dans le driver Java de définir une 'LoadBalancingPolicy' qui va déterminer quel noeud va servir une requête.
Par défaut un client peut interroger n'importe quel noeud (même si celui-ci ne possède pas la donnée: il jouera alors le rôle de coordinateur pour cette requête).

Cela peut être inefficace (donnée sur un autre noeud ou dans un autre datacenter).

Pour éviter cela nous allons [configurer une 'LoadBalancingPolicy'](https://academy.datastax.com/demos/getting-started-apache-cassandra-and-java-part-ii) de type 'TokenAwarePolicy' contenant une 'DCAwareRoundRobinPolicy' (pour être sûr que la requête reste dans le datacenter local)

## Data

![Smartphone Data v2](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/smartphone_data2.png)

### US6 : Pouvoir faire une recherche par date

Nous voulons pouvoir filtrer les résultats renvoyés par le service /data en fonction d'une date de début et d'une date de fin.

1. Vérifier que la modélisation choisie permet d'effectuer les requêtes demandées
2. Modifier le service afin de renvoyer les résultats filtrés

### US7 : Trier les résultats par date et type de données

Nous voulons en plus pouvoir trier les résultats en fonction de la date (de la plus récente à la plus ancienne ou le contraire) et/ou du type de données.

## Smartphone

### US8 : Seul le propriétaire du smartphone peut le supprimer

1. Ajouter le propriétaire à notre table `smartphones`
2. Ajouter le propriétaire du smartphone lors de la création du smartphone
3. Lors de la demande de suppression vérifier que le demandeur est bien le propriétaire (en une seule requête, sans faire de READ BEFORE WRITE)


## Utilisateur

![User Details v2](https://raw.githubusercontent.com/mNantern/formation-cassandra/master/exercices/data/media/user2.png)

### US9 : Ajout d'un smartphone à l'utilisateur

Lors de la création d'un smartphone il est nécessaire de l'ajouter à l'utilisateur sans quoi le smartphone ne pourra pas être consulté depuis la page utilisateur.

### US10 : Création concurrente d'utilisateur

Un problème est arrivé en production: deux utilisateurs ont choisit le même username (adresse email) au même moment. Le premier a perdu son compte sans aucune erreur.
Il est nécessaire de ne pas écraser un compte utilisateur si celui-ci existe déjà. Une erreur `HTTP 409 Conflict` doit être renvoyée dans ce cas.

Utiliser pour cela les [LWT](http://docs.datastax.com/en/developer/java-driver/3.0/java-driver/jd-faq.html#faq-conditional-statement).

### US11 : Remplacer l'identifiant du smartphone par son nom

**=> Retour aux slides !**

Dans notre cas nous allons considérer que le nom du smartphone ne change pas. Il faut donc dénormaliser les données en ajoutant également le nom du smartphone dans la ressource utilisateur.