package com.vivek.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vivek.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var PLAYER = true;
    private var turnCounts = 0;

    private lateinit var board: Array<Array<Button>>
    var boardStatus = Array(3) { IntArray(3) }
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //2D array defined as it has three rows , three columns
        //imagine this array
        //{ {0,1,2},
        // {3,4,5},
        // {6,7,8}  }
        board = arrayOf(
            arrayOf(binding.btn, binding.btn1, binding.btn2),
            arrayOf(binding.btn3, binding.btn4, binding.btn5),
            arrayOf(binding.btn6, binding.btn7, binding.btn8),
        )

        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)
                button.textSize = resources.getDimension(R.dimen._15ssp)
            }
        }

        binding.resetBtn.setOnClickListener {
            resetBoard()
        }
    }

    private fun display(tv: String) {
        binding.playerName.text = tv
        if (tv.contains("winner")) {
            disableButton()
        }
    }

    override fun onClick(p0: View?) {
        turnCounts++
        PLAYER = !PLAYER
        if (PLAYER) {
            display("Player X turn")
        } else {
            display("Player 0 turn")
        }

        if (turnCounts == 9) {
            binding.playerName.apply {
                text = "Game Draw"
                textSize = resources.getDimension(R.dimen._10ssp)
            }
        }

        when (p0?.id) {
            R.id.btn -> {
                updateValue(row = 0, col = 0, player = PLAYER)
            }
            R.id.btn1 -> {
                updateValue(row = 0, col = 1, player = PLAYER)
            }
            R.id.btn2 -> {
                updateValue(row = 0, col = 2, player = PLAYER)
            }
            R.id.btn3 -> {
                updateValue(row = 1, col = 0, player = PLAYER)
            }
            R.id.btn4 -> {
                updateValue(row = 1, col = 1, player = PLAYER)
            }
            R.id.btn5 -> {
                updateValue(row = 1, col = 2, player = PLAYER)
            }
            R.id.btn6 -> {
                updateValue(row = 2, col = 0, player = PLAYER)
            }
            R.id.btn7 -> {
                updateValue(row = 2, col = 1, player = PLAYER)
            }
            R.id.btn8 -> {
                updateValue(row = 2, col = 2, player = PLAYER)
            }
        }


    }

    /*
    * checking winners
    * */
    private fun checkWinner() {
        //horizontal
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]) {
                if (boardStatus[i][0] == 1) {
                    display("Player X winner")
                    break
                } else if (boardStatus[i][0] == 2) {
                    display("Player 0 winner")
                    break
                }
            }
        }

        //vertically
        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] == 1) {
                    display("Player X winner")
                    break
                } else if (boardStatus[0][i] == 2) {
                    display("Player 0 winner")
                    break
                }
            }
        }

        //first diagonally
        for (i in 0..2) {
            if (boardStatus[0][0]==boardStatus[1][1]&&boardStatus[0][0]==boardStatus[2][2]){
                if (boardStatus[0][0] == 1) {
                    display("Player X winner")
                    break
                } else if (boardStatus[0][0] == 2) {
                    display("Player 0 winner")
                    break
                }
            }
        }

        //second diagonally
        for (i in 0..2) {
            if (boardStatus[0][2]==boardStatus[1][1]&&boardStatus[0][2]==boardStatus[2][0]){
                if (boardStatus[0][2] == 1) {
                    display("Player X winner")
                    break
                } else if (boardStatus[0][2] == 2) {
                    display("Player 0 winner")
                    break
                }
            }
        }

    }

    private fun resetBoard() {
        turnCounts = 0
        PLAYER = true
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
            }
        }

        for (i in board) {
            for (button in i) {
                button.isEnabled = true
                button.text = ""
                button.background = null
                button.textSize = resources.getDimension(R.dimen._15ssp)
            }
        }
    }

    /*
    * disable button after winning
    * */
    private fun disableButton() {
        for (i in board) {
            for (button in i) {
                button.isEnabled = false
                button.textSize = resources.getDimension(R.dimen._20ssp)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val ticTacText = if (player) "X" else "0"
        val value = if (player) 1 else 2
        board[row][col].apply {
            isEnabled = false
            text = ticTacText
            if (player) {
                background = resources.getDrawable(R.color.orange)
            } else {
                background = resources.getDrawable(R.color.yellow)
            }
        }

        boardStatus[row][col] = value

        //there is only meaning of checking if plays 0,x is more than two times
        if (turnCounts > 4) {
            checkWinner()
        }

    }
}