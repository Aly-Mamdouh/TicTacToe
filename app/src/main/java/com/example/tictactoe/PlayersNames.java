package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PlayersNames extends AppCompatActivity {

    Button btn_submit;
    EditText et_player1,et_player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_names);

        btn_submit=findViewById(R.id.players_btn_submit);
        et_player1=findViewById(R.id.players_ed_pl1);
        et_player2=findViewById(R.id.players_ed_pl2);

        btn_submit.setOnClickListener(v -> {

            Intent intent=new Intent(getBaseContext(),Game.class);
            String player1_name=et_player1.getText().toString();
            String player2_name=et_player2.getText().toString();

            intent.putExtra("PLAYERS_NAMES",new String[]{player1_name,player2_name});
            startActivity(intent);
        });

    }
}