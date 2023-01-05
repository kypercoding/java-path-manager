# Java Path Manager

## Overview

The Java Path Manager is a small program (primarily intended for Linux machines) to keep track of a user's most commonly consulted directory or file paths. The program makes use of the Redis database and the Java programming language to keep track of users' path preferences, and can be used in conjunction with other Linux commands.

## Installation and Compilation

An executable JAR should already be provided in the "Releases" section of the repository. For this, the user only needs to have the following installed:
* Java
* Redis

Those who wish to generate a JAR locally must also have Maven installed. For these users, the following steps can be performed to generate an executable JAR:

```shell
git clone URL
cd java-path-manager
mvn clean compile assembly:single
```

By default, the name of the executable JAR should be "JavaPathManager.jar". Feel free to change this to whatever you wish afterwards. To run the program, the user can simply run (with arguments, explained later):

```shell
java -jar JavaPathManager.jar
```

Before running the executable, please make sure that you have started the redis-server service. Depending on how you installed redis, you may either need to call:

```shell
sudo systemctl start redis-server
```

or

```shell
sudo snap start redis
```

or some other command that starts the redis server.

In a later section, "Usage - Applications", this guide goes over potential use cases for this program.

## Usage - Commands

The following are valid commands for the program. Some of the commands may require ```KEY``` or ```PATH``` or both. These are mandatory arguments, and should be inserted correctly. All of the commands feature optional arguments to pass in a specific ```HOST``` and ```PORT``` to the Redis connection. By default (when no optional arguments passed in), the host and port arguments are set to "127.0.0.1" and 6379, respectively.

### Setting a path

The following command sets a PATH to a user-defined KEY:

```shell
java -jar JavaPathManager.jar set KEY PATH [HOST] [PORT]
```

Theoretically, KEY can be any valid term, but it may be more convenient for a user to use a key that is short. For instance, to save the "/home" directory with a key "home", perform the following command:

```shell
java -jar JavaPathManager.jar set home /home
```

If you want to save the current directory path to a key, pass in "." like so:

```shell
java -jar JavaPathManager.jar set home .
```

Note that this program does not know how to handle (at least, not yet) replacing the "." character with the current directory. As such, any folders in the current directory **cannot** be added like so:

```shell
java -jar JavaPathManager.jar set home ./someDirInsideHome
```

To update a key's path, pass in the same key, but a different path:

```shell
java -jar JavaPathManager.jar set home /home/Documents
```

### Getting a path

To output a path, pass in an existing KEY to this command:

```shell
java -jar JavaPathManager.jar get KEY [HOST] [PORT]
```

### Removing a path

To delete a KEY and its corresponding path, pass in an existing KEY to this command:

```shell
java -jar JavaPathManager.jar remove KEY [HOST] [PORT]
```

### Listing all saved paths

To list all keys and their saved paths, call the following command:

```shell
java -jar JavaPathManager.jar list [HOST] [PORT]
```

### Resetting all data

To remove all saved directories, call the following command:

```shell
java -jar JavaPathManager.jar reset [HOST] [PORT]
```

## Usage - Applications

1. Quickly changing directories

Given the nature of this program, one may find it useful to combine this program with other Linux commands. For instance, a powerful way a user can make use of this program is to quickly change to a preferred directory. In order to do so, a user may save the "java -jar JavaPathManager.jar" command to an alias. Then, the user can call the alias with the "cd" program like so:

```shell
cd $(ALIAS get KEY)
```

2. Quickly opening a file

Since the program simply takes in paths and does not check if the path refers to a file or directory, one can use this program to quickly access and/or edit files. For instance, to call nano on .bashrc quickly, one can perform the follwing commands:

```shell
ALIAS set bashrc ~/.bashrc

cd (to some other directory far from .bashrc)

nano $(ALIAS get bashrc)
```
