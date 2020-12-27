package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import curtin.edu.au.weeklyspecials.Data.ShoppingListSingleton;

public class WebViewWooliesActivity extends AppCompatActivity
{
    private static String TAG = "WOOLIES ACTIVITY";
    private WebView webView;
    private EditText listItem;
    private ShoppingListSingleton shoppingList = ShoppingListSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_woolies);

        //Retrieve intent info - item to search
        Intent intent = getIntent();
        final String toSearch = intent.getExtras().getString("ITEM");

        Log.d(TAG, toSearch);

        //INSTANTIATE UI
        webView = (WebView)findViewById(R.id.webWooliesPage);
        listItem = (EditText)findViewById(R.id.editTAddItem);
        Button btnAddToList = (Button) findViewById(R.id.btnAddToList);
        Button btnColes = (Button) findViewById(R.id.btnOtherShop);
        Button btnNewSearch = (Button) findViewById(R.id.btnReturn);

        //WebView setup
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "https://www.woolworths.com.au/shop/search/specials?searchTerm=" + toSearch;
        webView.loadUrl(url);

        //INITIALISE BUTTONS
        btnColes.setText(R.string.coles);
        btnColes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(WebViewWooliesActivity.this,
                        WebViewColesActivity.class);
                intent.putExtra("ITEM", toSearch);

                WebViewWooliesActivity.this.finish();
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

        btnAddToList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast toast;
                String text;

                if(!TextUtils.isEmpty(listItem.getText()))
                {
                    String toAdd = listItem.getText().toString();
                    shoppingList.addItem(toAdd);
                    listItem.setText(null);
                    text = "SUCCESS: Item added to list";
                }
                else
                {
                    text = "ERROR: Enter an item to add...";
                }

                toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
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