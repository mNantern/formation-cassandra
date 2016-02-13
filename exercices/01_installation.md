# Installation de Cassandra

Deux manière d'installer Cassandra sont proposées:
* En tant que VM via Vagrant
* En tant que container via Docker

## Installation avec Vagrant
### Installation
1. Installer Virtualbox (les exécutables sont fournis dans la clé USB)
2. Installer Vagrant (les exécutables sont fournis dans la clé USB)
3. Se placer dans le dossier *tools/vagrant* et exécuter la commande suivante:

```bash
vagrant up
```

La machine virtuelle va démarrer et Cassandra sera accessible sur localhost:9042

### Utilisation
#### Démarrer la VM
```bash
vagrant up
```

#### Accéder à la VM:
```
vagrant ssh
```
#### Arrêter la VM: 
```
vagrant halt
```

## Installation avec Docker
