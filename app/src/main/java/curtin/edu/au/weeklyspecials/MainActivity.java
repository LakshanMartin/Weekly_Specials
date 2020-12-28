package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import curtin.edu.au.weeklyspecials.Data.WooliesListSingleton;

public class MainActivity extends AppCompatActivity
{
    private static String TAG = "MAIN ACTIVITY";
    private EditText edTxtSearchInput;
    private WooliesListSingleton shoppingList = WooliesListSingleton.getInstance();

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

        if(!shoppingList.getShoppingList().isEmpty())
        {
            btnShoppingList.setVisibility(View.VISIBLE);
        }

        btnShoppingList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(shoppingList.getShoppingList().isEmpty())
                {
                    String text = "ERROR: List is currently empty...";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    /*for(String item : shoppingList.getShoppingList())
                    {
                        Log.d("Item", item);
                    }*/
                }
            }
        });
    }
}