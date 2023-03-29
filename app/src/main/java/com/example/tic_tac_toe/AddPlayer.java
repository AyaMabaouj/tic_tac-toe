package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.resources.TextAppearance;

public class AddPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        TextView title = findViewById(R.id.title);
        EditText playerOne = findViewById(R.id.playerone);
        EditText playerTwo = findViewById(R.id.playertwo);
        Button start = findViewById(R.id.startGame);
        Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink);
        title.startAnimation(blinkAnimation);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getPlayerOneName = playerOne.getText().toString();
                String getPlayerTwoName = playerTwo.getText().toString();

                if(getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()){
                    Toast.makeText(AddPlayer.this,"Please enter player name",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent =new Intent(AddPlayer.this,MainActivity.class);
                    intent.putExtra("playerOne",getPlayerOneName);
                    intent.putExtra("playerTwo",getPlayerTwoName);
                    startActivity(intent);

                }
            }
        });
    }
}