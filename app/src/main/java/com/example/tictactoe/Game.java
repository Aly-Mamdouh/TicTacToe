package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Game extends AppCompatActivity {
    private TicTacToeBoard ticTacToeBoard;
    Button btn_home,btn_again;
    TextView tv_playerDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ticTacToeBoard=findViewById(R.id.ticTacToeBoard);
        btn_home=findViewById(R.id.game_home);
        btn_again=findViewById(R.id.game_again);
        tv_playerDisplay=findViewById(R.id.game_playerDisplay);

        btn_again.setVisibility(View.GONE);
        btn_home.setVisibility(View.GONE);

       String[] playerNames=getIntent().getStringArrayExtra("PLAYERS_NAMES");
        if(playerNames!=null){
            tv_playerDisplay.setText((playerNames[0]+"'s Turn"));
        }
        ticTacToeBoard.setUpGame(btn_again,btn_home,tv_playerDisplay,playerNames);

        btn_home.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        });

        btn_again.setOnClickListener(v -> {
            ticTacToeBoard.resetGame();
           ticTacToeBoard.invalidate();

        });

    }

}