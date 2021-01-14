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
import android.widget.ImageButton;
import android.widget.Toast;

import curtin.edu.au.weeklyspecials.Data.ColesListSingleton;
import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Database.ShoppingListDb;

public class WebViewColesActivity extends AppCompatActivity
{
    private static String TAG = "WOOLIES ACTIVITY";
    private WebView webView;
    private EditText itemDesc, itemCost, itemQty;
    private ColesListSingleton shoppingList = ColesListSingleton.getInstance();
    private ShoppingListDb db = new ShoppingListDb();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_coles);

        String url;
        Button btnWoolies, btnViewList;

        //Retrieve intent info - item to search
        Intent intent = getIntent();
        final String toSearch = intent.getExtras().getString("ITEM");
        final boolean checkSpecials = intent.getExtras().getBoolean("SPECIALS");

        Log.d(TAG, toSearch);
        Log.d(TAG, String.valueOf(checkSpecials));

        //OPEN Database
        db.open(this);

        //INSTANTIATE UI
        webView = (WebView)findViewById(R.id.webColesPage);
        itemDesc = (EditText)findViewById(R.id.editTAddItem);
        itemCost = (EditText)findViewById(R.id.editTItemCost);
        itemQty = (EditText)findViewById(R.id.editTQuantity);
        ImageButton imgBAddToList = (ImageButton)findViewById(R.id.imgBAddToList);
        btnWoolies = (Button)findViewById(R.id.btnOtherShop);
        ImageButton imgBNewSearch = (ImageButton)findViewById(R.id.imgBNewSearch);
        btnViewList = (Button)findViewById(R.id.btnViewList);

        //WebView setup
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(checkSpecials)
        {
            url = "https://shop.coles.com.au/a/riverton/specials/search/" + toSearch;
        }
        else
        {
            url = "https://shop.coles.com.au/a/riverton/everything/search/" + toSearch;
        }

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
                intent.putExtra("SPECIALS", checkSpecials);

                WebViewColesActivity.this.finish();
                startActivity(intent);
            }
        });

        imgBNewSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        btnViewList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /*if(shoppingList.getShoppingList().isEmpty())
                {
                    String text = "ERROR: List is currently empty...";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Intent intent = new Intent(WebViewColesActivity.this,
                            ShoppingListsActivity.class);

                    //LIST_ID sets shopping list tab to position 1, which is the Coles tab
                    intent.putExtra("LIST_ID", 1);

                    startActivity(intent);
                }*/

                Intent intent = new Intent(WebViewColesActivity.this,
                        ShoppingListsActivity.class);

                //LIST_ID sets shopping list tab to position 1, which is the Coles tab
                intent.putExtra("LIST_ID", 1);

                startActivity(intent);
            }
        });

        imgBAddToList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast toast;
                String text;
                double cost;
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
                    if(!TextUtils.isEmpty(itemCost.getText()))
                    {
                        try
                        {
                            cost = Double.parseDouble(itemCost.getText().toString());
                            itemCost.setText(null);
                        }
                        catch (NumberFormatException nfe)
                        {
                            text = "ERROR: Invalid cost entered. Defaulting to $0.00";
                            toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                            toast.show();
                            cost = 0.00;
                        }
                    }
                    else
                    {
                        cost = 0.00;
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
                            itemQty.setText(null);
                        }
                        catch (NumberFormatException nfe)
                        {
                            text = "ERROR: Invalid Qty entered. Defaulting to 0";
                            toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                            toast.show();
                            qty = 0;
                        }
                    }

                    //Add new item to shopping list
                    String desc = itemDesc.getText().toString();
                    ItemData newItem = new ItemData(desc, cost, qty);
                    shoppingList.addItem(newItem);
                    itemDesc.setText(null);

                    //Add new item to database
                    db.addColesItem(newItem);

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