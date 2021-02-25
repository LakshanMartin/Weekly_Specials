package curtin.edu.au.weeklyspecials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import curtin.edu.au.weeklyspecials.Helpers.EditItemDialog;
import curtin.edu.au.weeklyspecials.Helpers.PagerAdapter;

public class ShoppingListsActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton imgBEmail, imgBSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        tabLayout = findViewById(R.id.tabBar);
        TabItem wooliesTab = findViewById(R.id.wooliesTab);
        TabItem colesTab = findViewById(R.id.colesTab);
        viewPager = findViewById(R.id.viewPager);
        imgBEmail = (ImageButton) findViewById(R.id.imgBEmail);
        imgBSMS = (ImageButton) findViewById(R.id.imgBSMS);

        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //RETRIEVE Intent to identify which shopping list to show
        Intent intent = getIntent();
        int listID = intent.getExtras().getInt("LIST_ID");
        setActiveTab(listID);

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

        imgBEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ShoppingListsActivity.this, EmailListActivity.class);
                startActivity(intent);
            }
        });

        imgBSMS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ShoppingListsActivity.this, SMSListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setActiveTab(int listID)
    {
        viewPager.setCurrentItem(listID);
    }
}