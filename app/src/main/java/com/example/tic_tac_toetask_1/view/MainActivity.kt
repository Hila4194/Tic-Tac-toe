package com.example.tic_tac_toetask_1.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tic_tac_toetask_1.R
import com.example.tic_tac_toetask_1.model.Game_View

class MainActivity : AppCompatActivity()
{
    private val viewModel = Game_View()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupGame()
    }

    private fun setupGame()
    {
        // Initialize the buttons for the game board (3x3 grid)
        val buttons = arrayOf(
            arrayOf(
                findViewById<ImageView>(R.id.cell00),
                findViewById(R.id.cell01),
                findViewById(R.id.cell02)
            ),
            arrayOf(
                findViewById<ImageView>(R.id.cell10),
                findViewById(R.id.cell11),
                findViewById(R.id.cell12)
            ),
            arrayOf(
                findViewById<ImageView>(R.id.cell20),
                findViewById(R.id.cell21),
                findViewById(R.id.cell22)
            )
        )

        buttons.forEachIndexed { row, rowButtons ->
            rowButtons.forEachIndexed { col, button ->
                button.setOnClickListener {
                    viewModel.makeMove(row, col)
                }
            }
        }

        val message = findViewById<TextView>(R.id.game_messege)
        val resetButton = findViewById<Button>(R.id.play_again_button)

        val player_x_score = findViewById<TextView>(R.id.player_x_score)
        val player_o_score = findViewById<TextView>(R.id.player_o_score)

        viewModel.setOnBoardUpdatedListener { board ->
            board.forEachIndexed { row, rowValues ->
                rowValues.forEachIndexed { col, value ->
                    val imageView = buttons[row][col]
                    when (value) {
                        "X" -> imageView.setImageResource(R.drawable.x_icon) // Set to "X" image
                        "O" -> imageView.setImageResource(R.drawable.o_icon) // Set to "O" image
                        else -> imageView.setImageResource(R.drawable.square_icon) // Set to empty image
                    }

                    val currentPlayer = viewModel.getCurrentPlayer()
                    message.text = "Player $currentPlayer's turn"
                }
            }

            viewModel.setOnGameOverListener { winner ->
                if (winner != null) {
                    message.text = "Winner: $winner"
                } else {
                    message.text = "It's a draw!"
                }
                resetButton.visibility = View.VISIBLE

                player_x_score.text = "Player X: ${viewModel.getXScore()}"
                player_o_score.text = "Player O: ${viewModel.getOScore()}"

            }

            resetButton.setOnClickListener {
                viewModel.resetGame()
                resetButton.visibility = View.GONE
                message.text = "Player X's turn"
            }
        }
    }
}