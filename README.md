# Chat

## Table of Contents

- [Chat](#chat)
  - [Table of Contents](#table-of-contents)
  - [About ](#about-)
  - [Getting Started ](#getting-started-)
  - [Usage ](#usage-)
  - [Contributing ](#contributing-)

## About <a name = "about"></a>

Ce projet est un chat pour le projet de SAE. Il s'agit d'un chat simple avec un serveur et un client. Le serveur est en java.

## Getting Started <a name = "getting_started"></a>

Pour lancer ce projet, vous devez installer java et javaFX.

## Usage <a name = "usage"></a>

Lancer le serveur avec la commande suivante :

```bash
java -jar Server.jar
```

Sur un autre terminal, lancer le client avec la commande suivante :

```bash
java --module-path "{JAVAFX_DIR}\lib" --add-modules javafx.controls,javafx.fxml -cp bin launch.ChatApplication
```

Une fois le client lancé, vous pouvez vous connecter avec un pseudo. Et utiliser la commande /help pour voir les commandes disponibles.
Pour ce connecter sur un autre ordinateur sur le même réseau, lancer <code> /ip </code> pour avoir l'adresse ip du serveur.   
Ensuite, lancer le client sur l'autre ordinateur et quand l'adresse ip est demandée, entrer l'adresse ip obtenue grâce au client local.   

Vous pouvez maintenant découvrir le chat.

## Contributing <a name = "contributor"></a>
Paul JUPILLIAT
Benjamin GUERRE
