package com.example.tictactoe;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameLogic {

private int[][] gameBoard;
private int player=1;
private String[] playersName={"Player 1","Player 2"};
private Button homeBtn;
private Button playAgainBtn;
private TextView playerTurn;
private int[] winType={-1,-1,-1};



    public GameLogic() {
        gameBoard=new int[3][3];
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                gameBoard[r][c]=0;
            }
        }

    }

    public boolean winnerCheck() {
        boolean isWin = false;

        // Hor Check
        for (int r = 0; r < 3; r++) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                winType = new int[]{r, 0, 1};
                isWin = true;
            }
        }
        // Ver Check
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] && gameBoard[0][c] != 0) {
                winType = new int[]{0, c, 2};
                isWin = true;
            }
        }
        // Neg Diagonal
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = new int[]{0, 2, 3};
            isWin = true;
        }

         // Pos Diagonal
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0) {
            winType = new int[]{2, 2, 4};
            isWin = true;
        }


         int boardFailed = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] != 0) {
                    boardFailed += 1;
                }
            }
        }

        if (isWin) {
            homeBtn.setVisibility(View.VISIBLE);
            playAgainBtn.setVisibility(View.VISIBLE);
            playerTurn.setText((playersName[player - 1] + " Won!!!!!"));
            playerTurn.setTextColor(Color.GREEN);
            return true;
        } else if (boardFailed == 9) {
            homeBtn.setVisibility(View.VISIBLE);
            playAgainBtn.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!!!!");
            playerTurn.setTextColor(Color.RED);
            return true;
        } else {
            return false;
        }

    }

    public void resetGame(){
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                gameBoard[r][c]=0;
            }
        }
        player=1;
        homeBtn.setVisibility(View.GONE);
        playAgainBtn.setVisibility(View.GONE);
        playerTurn.setText((playersName[0]+"'s Turn"));
        playerTurn.setTextColor(Color.BLACK);
    }

    public boolean updateGameBoard(int row , int col){

        if(gameBoard[row-1][col-1]==0){
            gameBoard[row-1][col-1]=player;
            if(player==1){
                playerTurn.setText((playersName[1]+"'s Turn"));
                playerTurn.setTextColor(Color.BLACK);
            }else{
                playerTurn.setText((playersName[0]+"'s Turn"));
                playerTurn.setTextColor(Color.BLACK);
            }

            return true;
        }
        else{
            return false;
        }

    }

    public int[][] getGameBoard(){
        return gameBoard;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setHomeBtn(Button homeBtn) {
        this.homeBtn = homeBtn;
    }

    public void setPlayAgainBtn(Button playAgainBtn) {
        this.playAgainBtn = playAgainBtn;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayersName(String[] playersName) {
        this.playersName = playersName;
    }
    public int[] getWinType() {
        return winType;
    }
}
