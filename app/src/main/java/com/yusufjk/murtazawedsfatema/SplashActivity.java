package com.yusufjk.murtazawedsfatema;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 2000;
    private ImageView logoImg;
    private Handler startupHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logoImg = findViewById(R.id.logoImg);

    }

    @SuppressLint("HandlerLeak")
    public void onStart() {
        super.onStart();


        startupHandler = new Handler() {
            public void handleMessage(Message msg) {
                logoImg.setVisibility(View.VISIBLE);
            }

        };

        startupHandler.postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                startupHandler.sendEmptyMessage(0);


            }
        }, DELAY_MILLIS);

    }

    @Override
    public void onBackPressed() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);

        View exitView = getLayoutInflater().inflate(R.layout.exit_dialog, null);
        Button exitBtn = exitView.findViewById(R.id.exitButton);
        Button cancelBtn = exitView.findViewById(R.id.cancelButton);
        builder.setView(exitView);
        final AlertDialog exitDialog = builder.create();
        exitDialog.show();
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                exitDialog.dismiss();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });

    }
}
