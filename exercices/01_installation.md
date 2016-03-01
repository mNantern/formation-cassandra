# Installation de Cassandra

Deux manière d'installer Cassandra sont proposées:
* En tant que VM via Vagrant
* En tant que container via Docker

## Installation avec Vagrant
### Installation
1. Installer Virtualbox (les exécutables sont fournis dans la clé USB)
2. Installer Vagrant (les exécutables sont fournis dans la clé USB)
3. Importer l'image de base:
```
vagrant box add ~/<path_to_usb_key>/cassandra_iot.box --name cassandra_iot
```
4. Se placer dans le dossier *tools/vagrant* et exécuter la commande suivante:

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

Si vous préférez utiliser Docker il existe une image officielle [disponible sur le docker Hub](https://hub.docker.com/_/cassandra/)
