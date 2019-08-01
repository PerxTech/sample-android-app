package com.perx.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_PERMISSION_INTERNET = 1;
    WebView wv = null;
    EditText urlEdit = null;

    EditText userIdEdit = null;
    EditText campaignIdEdit = null;
    EditText tokenEdit = null;

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

        this.urlEdit = this.findViewById(R.id.url);

        this.userIdEdit = this.findViewById(R.id.userId);
        this.campaignIdEdit = this.findViewById(R.id.campaignId);
        this.tokenEdit = this.findViewById(R.id.token);

        this.okBtn = this.findViewById(R.id.okBtn);

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

        Map<String, EditText> ps = new HashMap<>();
        ps.put("campaignId", this.campaignIdEdit);
        ps.put("token", this.tokenEdit);
        ps.put("pi", this.userIdEdit);
        for (Map.Entry<String, EditText> param : ps.entrySet()) {
            String v = param.getValue().getText().toString();
            if(!TextUtils.isEmpty(v)) {
                uri.appendQueryParameter(param.getKey(), v);
            }
        }

        url = uri.build().toString();
        this.wv.loadUrl(url);
        Snackbar.make(this.wv, url, Snackbar.LENGTH_SHORT).show();
    }

    private class MyChromeWebViewClient extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e(MainActivity.class.toString(), consoleMessage.lineNumber() + "," + consoleMessage.message() + "," + consoleMessage.messageLevel() + "," + consoleMessage.sourceId());
            return true;
        }
    }
}
