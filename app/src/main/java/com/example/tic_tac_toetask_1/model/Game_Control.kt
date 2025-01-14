package com.example.tic_tac_toetask_1.model

class Game_Control (
    val board: Array<Array<String>> = Array(3) { Array(3) { "" } },
    var currentPlayer: String = "X",
    var winner: String? = null,
    var isTheGameOver: Boolean = false
)
{
    // Return true if the move is valid and false if the move is invalid
    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col].isEmpty() && !isTheGameOver) {
            board[row][col] = currentPlayer
            checkWinner()
            if (!isTheGameOver) {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
            return true
        }
        return false
    }

    // Check if the current player has won the game
    private fun checkWinner()
    {
        for (i in 0..2)
        {
            if (board[i][0] == currentPlayer &&
                board[i][1] == currentPlayer &&
                board[i][2] == currentPlayer)
            {
                winner = currentPlayer
                isTheGameOver = true
                return
            }
            if (board[0][i] == currentPlayer &&
                board[1][i] == currentPlayer &&
                board[2][i] == currentPlayer)
            {
                winner = currentPlayer
                isTheGameOver = true
                return
            }
        }
        if (board[0][0] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][2] == currentPlayer ||
            board[0][2] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][0] == currentPlayer)
        {
            winner = currentPlayer
            isTheGameOver = true
            return
        }

        if (board.all { row -> row.all { cell -> cell.isNotEmpty() } })
        {
            isTheGameOver = true
        }
    }

    // Reset the game
    fun resetGame() {
        for (row in board) row.fill("")
        currentPlayer = "X"
        winner = null
        isTheGameOver = false
    }
}