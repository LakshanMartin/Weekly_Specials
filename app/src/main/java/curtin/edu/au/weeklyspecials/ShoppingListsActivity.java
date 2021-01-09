package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import curtin.edu.au.weeklyspecials.Data.ColesListSingleton;
import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Data.WooliesListSingleton;
import curtin.edu.au.weeklyspecials.Database.ShoppingListDb;
import curtin.edu.au.weeklyspecials.Helpers.PagerAdapter;

public class ShoppingListsActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button saveLists, clearLists;
    private WooliesListSingleton wooliesData = WooliesListSingleton.getInstance();
    private ColesListSingleton colesData = ColesListSingleton.getInstance();
    private ShoppingListDb db = new ShoppingListDb();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        tabLayout = findViewById(R.id.tabBar);
        TabItem wooliesTab = findViewById(R.id.wooliesTab);
        TabItem colesTab = findViewById(R.id.colesTab);
        viewPager = findViewById(R.id.viewPager);
        clearLists = findViewById(R.id.btnClearList);
        saveLists = (Button)findViewById(R.id.btnSaveLists);

        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //RETRIEVE Intent to identify which shopping list to show
        Intent intent = getIntent();
        int listID = intent.getExtras().getInt("LIST_ID");
        setActiveTab(listID);

        //Open database
        db.open(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        clearLists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Clear database and existing lists
                db.deleteRecords();
                wooliesData.freshList();
                colesData.freshList();

                //Refresh activity with fresh lists
                finish();
                startActivity(getIntent());
            }
        });

        saveLists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String text;

                //Clear existing records
                db.deleteRecords();

                if(!wooliesData.isEmpty() || !colesData.isEmpty())
                {
                    if(!wooliesData.isEmpty())
                    {
                        List<ItemData> wooliesList = wooliesData.getShoppingList();

                        for(int i = 0; i < wooliesList.size(); i++)
                        {
                            db.addWooliesItem(wooliesList.get(i));
                        }
                    }

                    if(!colesData.isEmpty())
                    {
                        List<ItemData> colesList = colesData.getShoppingList();

                        for(int i = 0; i < colesList.size(); i++)
                        {
                            db.addColesItem(colesList.get(i));
                        }
                    }

                    text = "Shopping Lists successfully saved!";
                }
                else
                {
                    text = "ERROR: No shopping lists created yet...";
                }

                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void setActiveTab(int listID)
    {
        viewPager.setCurrentItem(listID);
    }
}