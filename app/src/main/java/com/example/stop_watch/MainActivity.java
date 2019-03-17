package com.example.stop_watch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    long time;
    boolean start=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start_stop = findViewById(R.id.button);
        Button reset = findViewById(R.id.button2);
        final TextView  tv = findViewById(R.id.textView);

        OnClickListener oclBtn = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button: time=System.nanoTime();start=!start; break;
                    case R.id.button2: start=false;tv.setText("00:00:00:000:000"); break;
                }
            }
        };
        start_stop.setOnClickListener(oclBtn);
        reset.setOnClickListener(oclBtn);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    try {
                        Thread.sleep(1000/60);
                            runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    Button start_stop = findViewById(R.id.button);
                                    Button reset = findViewById(R.id.button2);
                                    if (start)
                                    {
                                        long us=(System.nanoTime()-time)/1000;
                                        long ms=us/1000;
                                        long sec=ms/1000;
                                        long min=sec/60;
                                        sec=sec % 60;
                                        ms=ms % 1000;
                                        us=us % 1000;
                                        long hour=sec/3600;
                                        String text =String.format ("%02d", hour)+":"+String.format ("%02d", min)+":"+String.format ("%02d", sec)+":"+String.format ("%03d", ms)+":"+String.format ("%03d", us);
                                        TextView  tv = findViewById(R.id.textView);
                                        tv.setText(text);
                                        start_stop.setText("стоп");
                                        reset.setClickable(true);
                                    }
                                    else
                                    {
                                        start_stop.setText("старт");
                                        reset.setClickable(false);
                                    }


                                }
                            });


                    } catch (InterruptedException e) {}
                }
            }
        }).start();
    }

}
