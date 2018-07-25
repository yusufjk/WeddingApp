package com.yusufjk.murtazawedsfatema;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    ProgressDialog mProgress;

    private WebView web;

    @Override

    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        web.saveState(outState);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = findViewById(R.id.webView);
        WebSettings webSettings = web.getSettings();
        if (isOnline()) {
            if (savedInstanceState != null) {
                web.restoreState(savedInstanceState);
            } else {


                webSettings.setJavaScriptEnabled(true);
                web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

                mProgress = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
                mProgress.setCancelable(true);

                web.setWebViewClient(new WebViewClient() {
                    // load url
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        String url = request.getUrl().toString();
                        mProgress.show();
                        view.loadUrl(url);
                        return true;
                    }

                    // when finish loading page
                    public void onPageFinished(WebView view, String url) {
                        if (mProgress.isShowing()) {
                            mProgress.dismiss();
                        }
                    }
                });
                web.loadUrl("https://goo.gl/forms/au6dxAP98fS8gZO33");
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            View exitView = getLayoutInflater().inflate(R.layout.exit_dialog, null);
            Button exitBtn = exitView.findViewById(R.id.exitButton);
            Button cancelBtn = exitView.findViewById(R.id.cancelButton);
            builder.setView(exitView);
            final AlertDialog exitDialog = builder.create();
            exitDialog.show();
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);

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
}
