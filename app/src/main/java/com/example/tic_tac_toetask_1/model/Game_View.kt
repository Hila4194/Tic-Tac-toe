package com.example.tic_tac_toetask_1.model

class Game_View
{
    private val game = Game_Control()

    private var onBoardUpdated: ((Array<Array<String>>) -> Unit)? = null
    private var onGameOver: ((String?) -> Unit)? = null
    private var x_Score = 0
    private var o_Score = 0

    // Set the listeners
    fun setOnBoardUpdatedListener(listener: (Array<Array<String>>) -> Unit) {
        onBoardUpdated = listener
    }

    fun setOnGameOverListener(listener: (String?) -> Unit) {
        onGameOver = listener
    }

    // Make a move on the game board
    fun makeMove(row: Int, col: Int) {
        if (game.makeMove(row,col)) {
            onBoardUpdated?.invoke(game.board)
            if (game.isTheGameOver) {
                if (game.winner == "X") {
                    x_Score++
                } else if (game.winner == "O") {
                    o_Score++
                }
                onGameOver?.invoke(game.winner)
            }
        }
    }

    // Reset the game
    fun resetGame() {
        game.resetGame()
        onBoardUpdated?.invoke(game.board)
        onGameOver?.invoke(null)
    }

    // Get the current player
    fun getCurrentPlayer(): String {
        return game.currentPlayer
    }

    // Get the scores
    fun getXScore(): Int {
        return x_Score
    }

    fun getOScore(): Int {
        return o_Score
    }
}