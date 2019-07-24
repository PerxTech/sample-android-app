package com.perx.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_PERMISSION_INTERNET = 1;
    WebView wv = null;
    EditText urlEdit = null;
    EditText idEdit = null;
    EditText campaignIdEdit = null;
    Spinner idType = null;
    Button okBtn = null;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.wv = this.findViewById(R.id.wv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings webSettings = this.wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        this.wv.setWebChromeClient(new MyChromeWebViewClient());
        this.wv.setWebViewClient(new MyWebViewClient());

        this.urlEdit = this.findViewById(R.id.url);
        this.idEdit = this.findViewById(R.id.id);
        this.campaignIdEdit = this.findViewById(R.id.campaignId);
        this.okBtn = this.findViewById(R.id.okBtn);
        this.idType = this.findViewById(R.id.idType);

        this.okBtn.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.INTERNET};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_INTERNET);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_INTERNET) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        String url = this.urlEdit.getText().toString();
        Uri.Builder uri = Uri.parse(url).buildUpon();

        String campaignId = this.campaignIdEdit.getText().toString();
        uri.appendQueryParameter("campaignId", campaignId);

        String type = (String) this.idType.getSelectedItem();
        String id = this.idEdit.getText().toString();
        if (type.equals("Token")) {
            uri.appendQueryParameter("token", id);
        } else {
            uri.appendQueryParameter("pi", id);
        }

        url = uri.build().toString();
        this.wv.loadUrl(url);
        Snackbar.make(this.wv, url, Snackbar.LENGTH_SHORT).show();
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return handleUrl(view) || super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            this.handleUrl(view);
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            this.handleUrl(view);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            this.handleUrl(view);
            super.onPageCommitVisible(view, url);
        }

        private boolean handleUrl(WebView view) {
            String url = view.getUrl();
            if (url.contains("result")) {
                Uri uri = Uri.parse(url);
                String payload = uri.getQueryParameter("payload");
                Intent intent = new Intent(MainActivity.this, CongratsActivity.class);
                intent.putExtra(CongratsActivity.PAYLOAD, payload);
                MainActivity.this.startActivity(intent);

                return true;
            }

            return false;
        }
    }

    private class MyChromeWebViewClient extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e(MainActivity.class.toString(), consoleMessage.lineNumber() + "," + consoleMessage.message() + "," + consoleMessage.messageLevel() + "," + consoleMessage.sourceId());
            return true;
        }
    }
}
