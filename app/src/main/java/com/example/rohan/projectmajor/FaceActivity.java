package com.example.rohan.projectmajor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FaceActivity extends AppCompatActivity {
    WebView web;
    String url ="https://sh-mishra.github.io/faceWeb/";

    AlertDialog.Builder alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_face);
        web = findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().getAllowFileAccess();
        web.getSettings().getAllowFileAccessFromFileURLs();
        web.getSettings().getAllowUniversalAccessFromFileURLs();
        web.getSettings().getAllowContentAccess();
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        web.getSettings().setMediaPlaybackRequiresUserGesture(false);
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                web.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        alert=new AlertDialog.Builder(FaceActivity.this);
        alert.setTitle("EXIT !!!");
        alert.setMessage("Are you sure?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(FaceActivity.this, MainActivity.class));
                finish();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (web.canGoBack())
        {
            web.goBack();
        }
        else
            alert.show();
    }
}
