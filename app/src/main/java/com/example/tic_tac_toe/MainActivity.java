package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tic_tac_toe.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SeekBar mVolumeSeekBar;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    Dialog dialog , drawdialog , quitdialog;
    private int winningPlayerIndex = -1;
    ImageView backBtn;
    ActivityMainBinding binding;
    private  final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0,0,0,0,0,0,0,0,0};
    private int playerTurn = 1;
    private int totalSelectedBox =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
        mVolumeSeekBar = findViewById(R.id.volumeSeekBar);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMediaPlayer = MediaPlayer.create(this, R.raw.grooving);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volume = (float) currentVolume / (float) maxVolume;
        mMediaPlayer.setVolume(volume, volume);
        mVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = (float) progress / 100.0f;
                mMediaPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitDialogfun();
            }
        });
        dialog = new Dialog(this);
        drawdialog = new Dialog(this);
        quitdialog = new Dialog(this);

        combinationList.add(new int[]{0,1,2});
        combinationList.add(new int[]{3,4,5});
        combinationList.add(new int[]{6,7,8});
        combinationList.add(new int[]{0,3,6});
        combinationList.add(new int[]{1,4,7});
        combinationList.add(new int[]{2,5,8});
        combinationList.add(new int[]{2,4,6});
        combinationList.add(new int[]{0,4,6});
        combinationList.add(new int[]{0,4,8});


        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");
        binding.playeroneName.setText(getPlayerOneName);
        binding.playertwoName.setText(getPlayerTwoName);

        binding.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(0)){
                    performAction((ImageView) view, 0);
                }
            }
        });
        binding.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(1)){
                    performAction((ImageView) view, 1);
                }
            }
        });
        binding.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(2)){
                    performAction((ImageView) view, 2);
                }
            }
        });
        binding.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(3)){
                    performAction((ImageView) view, 3);
                }
            }
        });
        binding.img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(4)){
                    performAction((ImageView) view, 4);
                }
            }
        });
        binding.img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(5)){
                    performAction((ImageView) view, 5);
                }
            }
        });
        binding.img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(6)){
                    performAction((ImageView) view, 6);
                }
            }
        });
        binding.img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(7)){
                    performAction((ImageView) view, 7);
                }
            }
        });
        binding.img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBoxSelectable(8)){
                    performAction((ImageView) view, 8);
                }
            }
        });


    }
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        } else {
            mMediaPlayer = MediaPlayer.create(this, R.raw.grooving);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        } else {
            mMediaPlayer = MediaPlayer.create(this, R.raw.grooving);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }
    }


    private void performAction(ImageView img, int position) {

        if(playerTurn == 1) {
            img.setImageResource(R.drawable.pink_x);
            playerTurn = 2;
            boxPositions[position] = 1;
        } else if(playerTurn == 2) {
            img.setImageResource(R.drawable.orange_circle_removebg_preview);
            playerTurn = 1;
            boxPositions[position] = 2;
        }
        totalSelectedBox++;

        for(int i=0; i<combinationList.size(); i++){
            int[] combination = combinationList.get(i);
            if(boxPositions[combination[0]] == boxPositions[combination[1]] &&
                    boxPositions[combination[1]] == boxPositions[combination[2]] &&
                    boxPositions[combination[0]] != 0){
                // Player wins
                winningPlayerIndex = boxPositions[combination[0]] - 1;
                celebrateDialog(winningPlayerIndex);
                return;
            }
        }

        if(totalSelectedBox > 9) {
            // Draw
            DrawDialogfun();
        }
    }

    private void changePlayerTurn(int currentPlayerTurn){
        playerTurn = currentPlayerTurn;
        if(playerTurn == 1){
           binding.playeroneLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box);
        }else{
            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border);
            binding.playeroneLayout.setBackgroundResource(R.drawable.white_box);
        }
    }
    private boolean checkResults(){
        boolean response = false;
        for(int i = 0; i < combinationList.size();i++){
            final int[] combination = combinationList.get(i);
            if(boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]]== playerTurn &&
                    boxPositions[combination[2]]== playerTurn){
                response = true;
                break;
            }
        }
        return response;
    }

    private boolean isBoxSelectable(int boxPosition){
        boolean response = false;
        if(boxPositions[boxPosition] == 0){
            response = true;
        }
        return  response;
    }
    public void restartMatch(){
        boxPositions = new int[] {0,0,0,0,0,0,0,0,0};
        playerTurn = 1;
        totalSelectedBox = 1;

        binding.img1.setImageResource(R.drawable.white_box);
        binding.img2.setImageResource(R.drawable.white_box);
        binding.img3.setImageResource(R.drawable.white_box);
        binding.img4.setImageResource(R.drawable.white_box);
        binding.img5.setImageResource(R.drawable.white_box);
        binding.img6.setImageResource(R.drawable.white_box);
        binding.img7.setImageResource(R.drawable.white_box);
        binding.img8.setImageResource(R.drawable.white_box);
        binding.img9.setImageResource(R.drawable.white_box);

    }

    private void    quitDialogfun() {


        quitdialog.setContentView(R.layout.quit_dialog);
        quitdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        quitdialog.setCanceledOnTouchOutside(false);


        Button quitBtn = quitdialog.findViewById(R.id.quit_btn);
        Button continueBtn = quitdialog.findViewById(R.id.continue_btn);

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitdialog.dismiss();
                Intent intent = new Intent(MainActivity.this, AddPlayer.class);
                startActivity(intent);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitdialog.dismiss();
            }
        });
        quitdialog.show();
    }

    private void celebrateDialog(int playerIndex) {
        dialog.setContentView(R.layout.celebrate_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        LottieAnimationView animationView = dialog.findViewById(R.id.celebrate_animationView);
        LinearLayout linearLayout = dialog.findViewById(R.id.container_1);
        Button quitBtn = dialog.findViewById(R.id.offline_game_quit_btn);
        Button continueBtn = dialog.findViewById(R.id.offline_game_continue_btn);
        ImageView playerImg = dialog.findViewById(R.id.offline_game_player_img);

        if(playerIndex == 0) {
            playerImg.setImageResource(R.drawable.pink_x);
        } else if(playerIndex == 1) {
            playerImg.setImageResource(R.drawable.orange_circle_removebg_preview);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animationView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }, 2300);

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(MainActivity.this, AddPlayer.class);
                startActivity(intent);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                restartMatch();
            }
        });

        dialog.show();
    }
    private void DrawDialogfun() {


        drawdialog.setContentView(R.layout.draw_dialog);
        drawdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);


        Button quitBtn = drawdialog.findViewById(R.id.offline_game_draw_quit_btn);
        Button continueBtn = drawdialog.findViewById(R.id.offline_game_draw_continue_btn);

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawdialog.dismiss();
                Intent intent = new Intent(MainActivity.this, AddPlayer.class);
                startActivity(intent);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawdialog.dismiss();
                restartMatch();
            }
        });
        drawdialog.show();
    }

}