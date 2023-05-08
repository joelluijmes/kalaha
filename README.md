# Kalaha / Mancala

This repository contains my attempt for a programming assessment. The assessment was to create a web application
around the game kalaha / mancala in Java.

The game works as follows. There are two players, each with 6 pits and a store. The pits are filled with 4 stones each
at the start of the game. The players take turns to pick a pit and distribute the stones in the pits in a counter
clockwise direction. The store of the opponent is skipped. The rules are as follows:

- If the last stone lands in the player's store, they get another turn.
- If the last stone lands in an empty pit on the player's side, they get to take all the stones from the opposite pit
  and put them in their store.
  The game ends when one player has no more stones on their side. The player with the most stones in their store wins.

For more details, see [Wikipedia](https://en.wikipedia.org/wiki/Kalah).

If you want to learn more about the approach I took, see [docs/approach.md](./docs/approach.md).

## Overview

The application is a Spring Boot application. It exposes a REST API to play the game. The API is documented using
Swagger, which can be accessed at `/swagger-ui.html`. The application uses a PostgreSQL database to persist the games
and players.

There are two implementations of the game. The first is a functional-style implementation, which is used by the API,
see `src/main/java/dev/joell/kalaha/board/Board.java`. The second is an object-oriented implementation, which is not
included in the API due time constraints. It can be found at `src/main/java/dev/joell/kalaha/gamelogic`.

## Running the application

The application can be run using the following command. See `application.properties` for the database configuration.
By default it uses a in memory database, which is not persisted between restarts.

```shell
./gradlew bootRun
```

## Interacting with the API

> Note, the commands below assume you run the application on `localhost:8080`. However, for demo purposes, the
> application is deployed to my private Kubernetes cluster. You can interact with the API at:
> https://kalaha.joell.dev/

The following is an example interaction with the API using `curl`. The API is documented using Swagger, which can be
accessed at `/swagger-ui.html`.

```shell
# Create a player
curl http://localhost:8080/api/players --json '{"username": "joell", "password": "123qwe"}'
# eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJqb2VsbCJ9.cOwiSN_85gDKQ2wvtATmuUIiymYri0F1R_1umzhpjbABTx_yU8LNpKNRjPymJcujlqplrmqPb3xELyACKGYEHg
```

```shell
# List players
curl http://localhost:8080/api/players
# [{"id":1,"username":"joell","createdAt":"2023-05-09T00:21:14.885329"}]
```

```shell
# Create a game
curl -X POST http://localhost:8080/api/games --json '{"cupsPerPlayer": 6, "stonesPerCup": 4}'
# {"id":1,"board":[0,4,4,4,4,4,4,0,4,4,4,4,4,4],"idxPlayerAStore":0,"idxPlayerBStore":7,"cupsPerPlayer":6,"stonesPerCup":4,"idxCurrentPlayer":0}
```

```shell
# List games
curl http://localhost:8080/api/games
# [{"id":1,"board":[0,4,4,4,4,4,4,0,4,4,4,4,4,4],"idxPlayerAStore":0,"idxPlayerBStore":7,"cupsPerPlayer":6,"stonesPerCup":4,"idxCurrentPlayer":0}]
```

```shell
# List single game
curl http://localhost:8080/api/games/1
# {"id":1,"board":[0,4,4,4,4,4,4,0,4,4,4,4,4,4],"idxPlayerAStore":0,"idxPlayerBStore":7,"cupsPerPlayer":6,"stonesPerCup":4,"idxCurrentPlayer":0}
```

```shell
# List game in pretty ascii format
curl http://localhost:8080/api/games/1/pretty
#    A     1     2     3     4     5     6     B
# +-----+-----+-----+-----+-----+-----+-----+-----+
# |        4  |  4  |  4  |  4  |  4  |  4  |     |
# |  0  +-----+-----+-----+-----+-----+-----+  0  +
# |     |  4  |  4  |  4  |  4  |  4  |  4        |
# +-----+-----+-----+-----+-----+-----+-----+-----+
```

```shell
# Make a move
curl -X POST http://localhost:8080/api/games/1/move/2
# {"id":1,"board":[1,5,0,4,4,4,4,0,4,4,4,4,5,5],"idxPlayerAStore":0,"idxPlayerBStore":7,"cupsPerPlayer":6,"stonesPerCup":4,"idxCurrentPlayer":7}

curl http://localhost:8080/api/games/1/pretty
#    A     1     2     3     4     5     6     B
# +-----+-----+-----+-----+-----+-----+-----+-----+
# |        5  |  0  |  4  |  4  |  4  |  4  |     |
# |  1  +-----+-----+-----+-----+-----+-----+  0  +
# |     |  5  |  5  |  4  |  4  |  4  |  4        |
# +-----+-----+-----+-----+-----+-----+-----+-----+

# Make another move
curl -X POST http://localhost:8080/api/games/1/move/2
# {"id":1,"board":[1,5,0,4,4,4,4,1,5,5,5,5,0,5],"idxPlayerAStore":0,"idxPlayerBStore":7,"cupsPerPlayer":6,"stonesPerCup":4,"idxCurrentPlayer":7}

# Make illegal move
curl -X POST http://localhost:8080/api/games/1/move/2
# {"error":"Invalid cup index, cup is empty."}
```
