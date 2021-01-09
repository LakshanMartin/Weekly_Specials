package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import curtin.edu.au.weeklyspecials.Data.ColesListSingleton;
import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Data.WooliesListSingleton;
import curtin.edu.au.weeklyspecials.Database.ShoppingListDb;

public class MainActivity extends AppCompatActivity
{
    private static String TAG = "MAIN ACTIVITY";
    private EditText edTxtSearchInput;
    private WooliesListSingleton wooliesList = WooliesListSingleton.getInstance();
    private ColesListSingleton colesList = ColesListSingleton.getInstance();
    private ShoppingListDb db = new ShoppingListDb();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INSTANTIATE UI
        edTxtSearchInput = (EditText)findViewById(R.id.editTSearchEntry);
        final Switch switchSpecials = (Switch) findViewById(R.id.switchSpecial);
        Button btnWoolies = (Button) findViewById(R.id.btnWooliesSearch);
        Button btnColes = (Button) findViewById(R.id.btnColesSearch);
        Button btnShoppingList = (Button)findViewById(R.id.btnViewList);

        //OPEN Database
        db.open(this);
        loadWooliesData();
        loadColesData();

        //ACTIVATE Button
        btnWoolies.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!TextUtils.isEmpty(edTxtSearchInput.getText()))
                {
                    Intent intent = new Intent(MainActivity.this,
                            WebViewWooliesActivity.class);

                    String itemSearch = edTxtSearchInput.getText().toString();
                    Boolean switchState = switchSpecials.isChecked();

                    intent.putExtra("ITEM", itemSearch);
                    intent.putExtra("SPECIALS", switchState);
                    startActivity(intent);
                }
                else
                {
                    String text = "ERROR: Enter an item to search...";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnColes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!TextUtils.isEmpty(edTxtSearchInput.getText()))
                {
                    Intent intent = new Intent(MainActivity.this,
                            WebViewColesActivity.class);

                    String itemSearch = edTxtSearchInput.getText().toString();
                    Boolean switchState = switchSpecials.isChecked();

                    intent.putExtra("ITEM", itemSearch);
                    intent.putExtra("SPECIALS", switchState);
                    startActivity(intent);
                }
                else
                {
                    String text = "ERROR: Enter an item to search...";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnShoppingList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(wooliesList.getShoppingList().isEmpty() && colesList.getShoppingList().isEmpty())
                {
                    String text = "ERROR: List is currently empty...";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this,
                            ShoppingListsActivity.class);

                    //Defaults to the Woolies list tab first
                    intent.putExtra("LIST_ID", 0);

                    startActivity(intent);
                }
            }
        });
    }

    private void loadWooliesData()
    {
        Cursor cursor = db.fetchWoolies();
        String desc;
        double cost;
        int qty;

        try
        {
            while(cursor.moveToNext())
            {
                //Get record from database
                desc = cursor.getString(0);
                cost = Double.parseDouble(cursor.getString(1));
                qty = Integer.parseInt(cursor.getString(2));

                wooliesList.addItem(new ItemData(desc, cost, qty));
            }
        }
        finally
        {
            cursor.close();
        }
    }

    private void loadColesData()
    {
        Cursor cursor = db.fetchColes();
        String desc;
        double cost;
        int qty;

        try
        {
            while(cursor.moveToNext())
            {
                //Get record from database
                desc = cursor.getString(0);
                cost = Double.parseDouble(cursor.getString(1));
                qty = Integer.parseInt(cursor.getString(2));

                colesList.addItem(new ItemData(desc, cost, qty));
            }
        }
        finally
        {
            cursor.close();
        }
    }
}