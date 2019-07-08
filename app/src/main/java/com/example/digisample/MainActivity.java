package com.example.digisample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_PERMISSION_INTERNET = 1;
    WebView wv = null;
    EditText urlEdit = null;
    Button okBtn = null;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.wv = this.findViewById(R.id.wv);
        WebSettings webSettings = this.wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        this.wv.setWebViewClient(new MyWebViewClient());

        this.urlEdit = this.findViewById(R.id.url);
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
        this.wv.loadUrl(url);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (true) {
                //if (url.contains("congrats")) {
//                Uri uri = Uri.parse(url);
//                String payload = uri.getQueryParameter("payload");
                String payload = "eyJkYXRhIjp7ImlkIjo4NiwidXNlcl9hY2NvdW50X2lkIjo1ODUyLCJzdGF0ZSI6InJlZGVlbWVkIiwiY2FtcGFpZ25faWQiOjIzOSwiZ2FtZV9pZCI6MzQsIm91dGNvbWVzIjpbeyJvdXRjb21lX3R5cGUiOiJyZXdhcmQiLCJpZCI6MTQwNDI1LCJuYW1lIjoiRW5qb3kgMTAlIG9mZiBKb2xsaWJlZSBNZWFsIiwidmFsaWRfdG8iOiIyMDE5LTA4LTAxVDE2OjAwOjAwLjAwMFoiLCJ2b3VjaGVyX2NvZGUiOiJCMU5LT0xHTlU5S04xUlhNIiwidm91Y2hlcl9rZXkiOm51bGwsInZvdWNoZXJfdHlwZSI6ImNvZGUiLCJzdGF0ZSI6Imlzc3VlZCIsImdpdmVuX2J5IjpudWxsLCJnaXZlbl90byI6bnVsbCwiZ2l2ZW5fZGF0ZSI6bnVsbCwiaXNzdWVkX2RhdGUiOiIyMDE5LTA3LTA1VDA5OjQzOjQyLjY5OFoiLCJyZWRlbXB0aW9uX2RhdGUiOm51bGwsInJlc2VydmF0aW9uX2V4cGlyZXNfYXQiOm51bGwsInJlZGVtcHRpb25fdHlwZSI6eyJjYWxsX3RvX2FjdGlvbiI6bnVsbCwidGltZXIiOjAsInR5cGUiOiJvZmZsaW5lIn0sInJld2FyZCI6eyJpZCI6MTA1NDQsIm5hbWUiOiJFbmpveSAxMCUgb2ZmIEpvbGxpYmVlIE1lYWwiLCJkZXNjcmlwdGlvbiI6IkVuam95IDEwJSBvZmYgSm9sbGliZWUgTWVhbCIsInN1YnRpdGxlIjpudWxsLCJ2YWxpZF9mcm9tIjoiMjAxOS0wNy0wMVQwMToyNzowMC4wMDBaIiwidmFsaWRfdG8iOiIyMDE5LTA4LTAxVDE2OjAwOjAwLjAwMFoiLCJzZWxsaW5nX2Zyb20iOiIyMDE5LTA3LTAxVDAxOjI3OjAwLjAwMFoiLCJzZWxsaW5nX3RvIjoiMjAxOS0wOC0xNVQxNjowMDowMC4wMDBaIiwiaW1hZ2VzIjpbeyJ1cmwiOiJodHRwczovL3BlcngtY2RuLXN0YWdpbmcuczMuYW1hem9uYXdzLmNvbS9yZXdhcmQvaXRlbS9pbWFnZXMvMTA1NjkvMjIwcHgtam9sbGliZWVfMjAxMV9sb2dvLXN2Zy1kMWZjNmEzOS03ODg0LTRmOWItOTVjNy1iNWY1ZTFjMmJmODgucG5nIiwidHlwZSI6ImhlYWRlciJ9XSwiY3VzdG9tX2ZpZWxkcyI6e319LCJjdXN0b21fZmllbGRzIjp7ImFsdGVybmF0ZV9pZCI6ImEwMDA0In19XX19";
                Intent intent = new Intent(MainActivity.this, CongratsActivity.class);
                intent.putExtra(CongratsActivity.PAYLOAD, payload);
                MainActivity.this.startActivity(intent);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
