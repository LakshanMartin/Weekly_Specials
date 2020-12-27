package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewColesActivity extends AppCompatActivity
{
    private static String TAG = "WOOLIES ACTIVITY";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_coles);

        String url;
        Button btnWoolies, btnNewSearch;

        //Retrieve intent info - item to search
        Intent intent = getIntent();
        final String toSearch = intent.getExtras().getString("ITEM");

        Log.d(TAG, toSearch);

        //INSTANTIATE UI
        webView = (WebView)findViewById(R.id.webColesPage);
        btnWoolies = (Button)findViewById(R.id.btnOtherShop);
        btnNewSearch = (Button)findViewById(R.id.btnReturn);

        //WebView setup
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        url = "https://shop.coles.com.au/a/riverton/specials/search/" + toSearch;
        webView.loadUrl(url);

        //INITIALISE BUTTONS
        btnWoolies.setText(R.string.woolies);
        btnWoolies.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(WebViewColesActivity.this,
                        WebViewWooliesActivity.class);
                intent.putExtra("ITEM", toSearch);

                WebViewColesActivity.this.finish();
                startActivity(intent);
            }
        });

        btnNewSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}