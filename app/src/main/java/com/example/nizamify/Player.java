package com.example.nizamify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

public class Player extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton imgBtnStart;
    private ImageView img;
    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();;
    private TextView tx1,tx2,tx3;

    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("Uri");
        String soundName = intent.getStringExtra("SoundName");

        tx1 = (TextView)findViewById(R.id.textView2);
        tx2 = (TextView)findViewById(R.id.textView3);
        tx3 = (TextView)findViewById(R.id.textView4);
        imgBtnStart = (ImageButton)findViewById(R.id.start);
        img = (ImageView)findViewById(R.id.imageView);
        tx3.setText(soundName+".mp3");

        mediaPlayer = MediaPlayer.create(this, Uri.parse(uri));
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        super.onBackPressed();
    }

    public void musicstartstop(View v){
        if(!mediaPlayer.isPlaying()){
            musicplay(v);
        }else{
            musicpause(v);
        }
    }
    public void musicplay(View v)
    {
        Toast.makeText(getApplicationContext(), "Müzik çalıyor",Toast.LENGTH_SHORT).show();
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            oneTimeOnly = 1;
        }

        tx2.setText(String.format("%d : %d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        tx1.setText(String.format("%d : %d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

        imgBtnStart.setImageResource(R.drawable.pause);
        myHandler.postDelayed(UpdateSongTime,100);
    }

    public void musicpause(View v)
    {
        Toast.makeText(getApplicationContext(), "Müzik durduruldu",Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();
        imgBtnStart.setImageResource(R.drawable.play);
    }

    public void musicprevious(View v){

    }
    public void musicnext(View v){

    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            myHandler.postDelayed(this, 100);
        }
    };
}