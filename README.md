# Chat

## Table of Contents

- [Chat](#chat)
  - [Table of Contents](#table-of-contents)
  - [About ](#about-)
  - [Getting Started ](#getting-started-)
  - [Usage ](#usage-)
  - [Fonctionnalities ](#fonctionnalities-)
    - [Ip inconnue](#ip-inconnue)
    - [Nom d'utilisateur](#nom-dutilisateur)
    - [Salon Général](#salon-général)
    - [Home](#home)
    - [new salon](#new-salon)
    - [Tous les salons](#tous-les-salons)
    - [Users connectés](#users-connectés)
    - [Message privé](#message-privé)
    - [Uptime](#uptime)
    - [Quit](#quit)
    - [Help](#help)
  - [Contributing ](#contributing-)
>>>>>>> 4d4517757a12f0c0e8d8c6a92d940fa96b0ae099
  - [Contributing ](#contributing-)

## About <a name = "about"></a>

Ce projet est un chat pour le projet de SAE. Il s'agit d'un chat simple avec un serveur et un client. Le serveur est en java.

## Getting Started <a name = "getting_started"></a>

Pour lancer ce projet, vous devez installer java et javaFX.

## Usage <a name = "usage"></a>

Lancer le serveur avec la commande suivante :

```bash
java -jar Serveur.jar
```

Sur un autre terminal, lancer le client avec la commande suivante :

```bash
java --module-path "{JAVAFX_DIR}\lib" --add-modules javafx.controls,javafx.fxml -cp bin launch.ChatApplication
```

Une fois le client lancé, vous pouvez vous connecter avec un pseudo. Et utiliser la commande /help pour voir les commandes disponibles.
Pour ce connecter sur un autre ordinateur sur le même réseau, lancer <code> /ip </code> pour avoir l'adresse ip du serveur.   
Ensuite, lancer le client sur l'autre ordinateur et quand l'adresse ip est demandée, entrer l'adresse ip obtenue grâce au client local.

Vous pouvez maintenant découvrir le chat.

## Fonctionnalities <a name = "fonctionnali"></a>
### Ip inconnue
Si le client n'arrive 
### Nom d'utilisateur
Le nom d'utilisateur est unique. Si un utilisateur tente de se connecter avec un nom déjà utilisé, il lui est demandé d'en choisir un autre.

### Salon Général
Ce salon permet de discuter avec tous les utilisateurs connectés. Il est accessible par défaut.Et envoie le message à tous les autres salons.

### Home
Quand clique sur le bouton home, on revient en mode chat salon.

### new salon
Quand appui sur bouton "nouveau salon", on saisie le nom du salon que l'on veut créer. Si il n'est pas déjà pris cela le créer et on est ajouté dedans et déconnecté de l'ancien salon.

### Tous les salons
Quand appui sur bouton "tous les salons", on peut voir tous les salons existants. On peut cliquer sur un salon pour y accéder.

### Users connectés
Quand oon appui sur le bouton "Messages privés", on peut voir tous les utilisateurs connectés. On peut cliquer sur un utilisateur pour lui envoyer un message privé.

### Message privé
Pour envoyer le premier message privé à une personne cliquer sur "Messages privés" puis sur le nom de l'utilisateur avec qui on veut discuter. 
La personne reçois un message le bouton message privé devient rouge. Et le nom de la perssonne qui à envoyer aussi.
Pour répondre utiliser le textField du bas.

- To Do: Faire en sorte que les messages privéee soit visiible sans rechager le chat privé.

### Uptime
Permet de voir le temps qu'un salon existe.

### Quit
Quand on quitte le chat, par la croix ou par la commande <code> /quit </code>, on est déconnecté du serveur et on peut se reconnecter avec un autre pseudo (le pseudo actuel est libéré).

### Help
<code> /help </code> permet d'avoir la liste des commandes disponibles.



## Contributing <a name = "contributor"></a>
Paul JUPILLIAT
Benjamin GUERRE
