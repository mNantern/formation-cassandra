# FAQ

## Insertion d'un null

Dans Cassandra insérer une valeur null dans une colonne revient à supprimer cette colonne (identique au delete). La suppression d'une colonne implique la création d'un tombstone.
Dans le cas des "prepared statement" on ne dispose pas toujours de la valeur pour toutes les colonnes.
Jusqu'à la version 2.2 de C* cela impliquait d'insérer un tombstone pour chaque colonne valant "null" ce qui devenait problématique à long terme.

La solution était de déclarer de multiples prepared statement et de ne passer que des colonnes non null. Avec de nombreuses permutations possibles cela devenait rapidement pénible.

Depuis la résolution de la jira [CASSANDRA-7304](https://issues.apache.org/jira/browse/CASSANDRA-7304) il est maitenant possible avec le driver java d'utiliser la méthode `setToNull` sur un BoundStatement.

## Modifier le type d'une colonne de type Collection

Ce n'est pas possible de le faire avec Cassandra, cf [CASSANDRA-6276](https://issues.apache.org/jira/browse/CASSANDRA-6276), il faut créer une colonne avec un nom différent.
