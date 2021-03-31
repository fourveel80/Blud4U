package com.example.blood4u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class activity_preparation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);

        webView.loadUrl("https://blooddonationthai.com/?page_id=745#");
    }

    private class CustomWebViewClient extends WebViewClient{
        public boolean shouldOverriderUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
