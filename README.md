# ChatServer

## Description

This project implements a simple chat system using Java RMI (Remote Method Invocation). It includes a chat server, a command-line client interface, and a graphical client interface using Java Swing.

## Project Structure

- ChatServer.java: The chat server.
- ChatImpl.java: The chat server implementation.
- Chat_itf.java: The chat server interface.
- Client_itf.java: The chat client interface.
- ChatClient.java: The command-line chat client.
- ChatClientGUI.java: The graphical chat client.

## Prerequisites

- Java JDK 8 or higher
- Make

## Compilation

To compile the project, run the following command in the project directory:

```sh
make
```

This command will compile all the Java files and create the necessary JAR files.

## Execution

### Start the RMI Registry

Before starting the server or client, you need to start the RMI registry. Run the following commands:

```sh
export CLASSPATH=$CLASSPATH:$(pwd)/classes
rmiregistry <port> &
```

Replace `<port>` with the port on which you want the registry to listen (e.g., 6090).

### Start the Server

To start the chat server, run the following command:

```sh
java -classpath .:classes:lib/Chat_itf.jar:lib/ChatImpl.jar ChatServer <port>
```

Replace `<port>` with the port on which you started the RMI registry.

### Start the Command-Line Client

To start the command-line chat client, run the following command:

```sh
java -classpath .:classes:lib/Chat_itf.jar ChatClient <rmiregistry host> <rmiregistry port> <username>
```

Replace `<rmiregistry host>`, `<rmiregistry port>`, and `<username>` with the appropriate values.

### Start the Graphical Client

To start the graphical chat client, run the following command:

```sh
java -classpath .:classes:lib/Chat_itf.jar ChatClientGUI <rmiregistry host> <rmiregistry port> <username>
```

Replace `<rmiregistry host>`, `<rmiregistry port>`, and `<username>` with the appropriate values.

## Cleaning Up

To clean up the compiled files and JAR files, run the following command:

```sh
make clean
```

## Authors

- Girncuti Dany Carol (dany-carol.girincuti@etu.univ-grenoble-alpes.fr)
- Dagar Tiyo Vaneck Duramel (vaneck-duramel.dagar-tiyo@etu.univ-grenoble-alpes.fr)

```
