package unifi.testmove;
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class OpenGLES20Activity extends Activity implements View.OnClickListener {

    private static int MAXSLEEPTIME = 200;
    private MyGLSurfaceView mGLView;
    private String status = "ok";
    private boolean Pause = true;
    private boolean GameOn = true;
    private int tick=0;

    Thread background = new Thread(new Runnable() {
        public void run() {
            int maxtick = 4;
            try {

                while (GameOn) {

                    if (Pause) {
                        Thread.sleep(MAXSLEEPTIME);
                        if(tick<maxtick){
                            tick++;
                        }
                        else {
                            mGLView.testgiu();
                            status = mGLView.getStatus();
                            if ((status.equals("gameover")) || (status.equals("win"))) {
                                GameOn = false;
                            }
                            tick=0;
                        }
                        mGLView.requestRender();
                    }
                }
                playGame();
                System.exit(0);
            } catch (Throwable t) {
                System.out.println(t);
                System.out.println("Errore nel thread");
            }
        }
    });

    private ImageButton bLeft;
    private ImageButton bRight;
    private ImageButton bDown;
    private ImageButton bPause;
    private Chronometer clock;
    private int[] message = new int[2];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        setContentView(R.layout.activity_open_gles20);
        mGLView = (MyGLSurfaceView)findViewById(R.id.glSurfaceViewID);

        //Riprendo i dati di prima
        Bundle intent = getIntent().getExtras();
        message = intent.getIntArray("number");
        mGLView.setMessaggio(message);

        setupGame();
    }

    private void setupGame() {
        //Dichiarazione dei buttons
        bLeft = (ImageButton) findViewById(R.id.LeftButton);
        bRight = (ImageButton) findViewById(R.id.RightButton);
        bDown = (ImageButton) findViewById(R.id.DownButton);
        bPause = (ImageButton) findViewById(R.id.PauseButton);
        clock = (Chronometer) findViewById(R.id.Clock);
        //li metto in ascolto
        bLeft.setOnClickListener(this);
        bRight.setOnClickListener(this);
        bDown.setOnClickListener(this);
        bPause.setOnClickListener(this);

        clock.setBase(SystemClock.elapsedRealtime());
        clock.start();

        background.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
        Pause = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
        Pause = true;
    }

    //controllo sul click
    public void onClick(View v) {
        if (v.getId() == R.id.LeftButton) {
            if (Pause)
                mGLView.indietro();
        }
        if (v.getId() == R.id.RightButton) {
            if (Pause)
                mGLView.avanti();
        }
        if (v.getId() == R.id.DownButton) {
            if (Pause) {
                mGLView.forcedown();
                tick=0;
            }
        }
        if (v.getId() == R.id.PauseButton) {
            if (Pause) {
                Pause = false;
                //onPause();
                bPause.setBackgroundResource(R.mipmap.play_resume);
            } else {
                Pause = true;
                //onResume();
                bPause.setBackgroundResource(R.mipmap.pause_resume);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);

    }

    public void playGame() {
        status = mGLView.getStatus();
        clock.stop();
        long elapsedMillis = SystemClock.elapsedRealtime() - clock.getBase();
        int finaltimer = (int)(elapsedMillis / 1000);
        Intent intent = new Intent(OpenGLES20Activity.this, RiepilogoActivity.class);
        intent.putExtra("Tempo", status);
        intent.putExtra("Timer", finaltimer);
        startActivity(intent);
    }


}