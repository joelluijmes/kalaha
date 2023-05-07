package dev.joell.kalaha.wip.game;
// package dev.joell.kalaha.game;

// public class Game {

//     private final GameState state;

//     /** The amount of cups per player. */
//     final int cupsPerPlayer;

//     /** The amount of stones per cup. */
//     final int stonesPerCup;

//     public Game(int cupsPerPlayer, int stonesPerCup) {
//         this.cupsPerPlayer = cupsPerPlayer;
//         this.stonesPerCup = stonesPerCup;

//         this.state = new GameState();
//     }
    
//     class GameState {
//         /** Array containing the board state. Tracks the amount of stones per location (including stores). */
//         private final Cell[] board;

//         /** The index of the current player. */
//         private int idxCurrentPlayer;

//         public GameState() {
//             // board is the amount of cups per player, plus the stores for each player
//             this.board = new Cell[cupsPerPlayer * 2 + 2];
//             for (int i = 0; i < this.board.length; i++) {
//                 if (i != this.idxPlayerAStore && i != this.idxPlayerBStore) {
//                     this.board[i] = new Cell(i, stonesPerCup);
//                     // this.board[i] 
//                 }
//             }
    
//             // TODO: initialize?
//             this.idxCurrentPlayer = this.idxPlayerAStore;
//         }
//     }

//     public void makeMove(int cup) {
//          // TODO: make custom exceptions such they can be caught and handled by the controller
//          if (cup < 1 || cup > cupsPerPlayer) {
//             throw new IllegalArgumentException("Invalid cup index, must be between 1 and " + cupsPerPlayer + " inclusive.");
//         }

//         // Calculate the index of the cup in the board array. As we store player B's cups in reverse order, when B
//         // picks a cup, we actually need to subtract the cup index from the total amount of cups per player.
//         int idx = this.idxCurrentPlayer == this.idxPlayerAStore 
//             ? cup 
//             : this.board.length - cup;

//         int numStones = this.board[idx];
//         if (numStones == 0) {
//             throw new IllegalArgumentException("Invalid cup index, cup is empty.");
//         }
//     }
// }
