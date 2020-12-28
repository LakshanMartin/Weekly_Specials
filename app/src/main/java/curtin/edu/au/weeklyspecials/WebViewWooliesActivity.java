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

import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Data.WooliesListSingleton;

public class WebViewWooliesActivity extends AppCompatActivity
{
    private static String TAG = "WOOLIES ACTIVITY";
    private WebView webView;
    private EditText itemDesc, itemCost, itemQty;
    private WooliesListSingleton shoppingList = WooliesListSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_woolies);

        String url;

        //Retrieve intent info - item to search
        Intent intent = getIntent();
        final String toSearch = intent.getExtras().getString("ITEM");
        final boolean checkSpecials = intent.getExtras().getBoolean("SPECIALS");

        Log.d(TAG, toSearch);
        Log.d(TAG, String.valueOf(checkSpecials));

        //INSTANTIATE UI
        webView = (WebView)findViewById(R.id.webWooliesPage);
        itemDesc = (EditText)findViewById(R.id.editTAddItem);
        Button btnAddToList = (Button) findViewById(R.id.btnAddToList);
        Button btnColes = (Button) findViewById(R.id.btnOtherShop);
        Button btnNewSearch = (Button) findViewById(R.id.btnReturn);

        //WebView setup
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(checkSpecials)
        {
            url = "https://www.woolworths.com.au/shop/search/specials?searchTerm=" + toSearch;
        }
        else
        {
            url = "https://www.woolworths.com.au/shop/search/products?searchTerm=" + toSearch;
        }

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
                intent.putExtra("SPECIALS", checkSpecials);

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
                Double cost;
                int qty;

                /***
                 * VALIDATION CHECKS:
                 *  - Check that item description has been entered.
                 *      - Error if not
                 *      - If so, check that:
                 *          - Cost has been entered
                 *              - If not, then default to 0.00
                 *              - If so, check that valid double value entered
                 *          - Qty has been entered
                 *              - If not, then default to 0
                 *              - If so, check that valid int value entered
                 ***/
                if(!TextUtils.isEmpty(itemDesc.getText()))
                {
                    //VALIDATION - cost input
                    if(TextUtils.isEmpty(itemCost.getText()))
                    {
                        cost = 0.00;
                    }
                    else
                    {
                        try
                        {
                            cost = Double.parseDouble(itemCost.getText().toString());
                        }
                        catch (NumberFormatException nfe)
                        {
                            text = "ERROR: Invalid cost entered. Defaulting to $0.00";
                            toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                            toast.show();
                            cost = 0.00;
                        }
                    }

                    //VALIDATION - qty input
                    if(TextUtils.isEmpty(itemQty.getText()))
                    {
                        qty = 0;
                    }
                    else
                    {
                        try
                        {
                            qty = Integer.parseInt(itemQty.getText().toString());
                        }
                        catch (NumberFormatException nfe)
                        {
                            text = "ERROR: Invalid Qty entered. Defaulting to 0";
                            toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                            toast.show();
                            qty = 0;
                        }
                    }

                    String desc = itemDesc.getText().toString();
                    shoppingList.addItem(new ItemData(desc, cost, qty));
                    itemDesc.setText(null);
                    text = "SUCCESS: Item added to list";
                }
                else
                {
                    text = "ERROR: Enter an item description to add to list...";
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