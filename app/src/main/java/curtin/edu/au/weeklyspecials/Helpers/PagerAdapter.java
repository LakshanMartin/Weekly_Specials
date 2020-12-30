package curtin.edu.au.weeklyspecials.Helpers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import curtin.edu.au.weeklyspecials.Fragments.ColesListFragment;
import curtin.edu.au.weeklyspecials.Fragments.WooliesListFragment;

public class PagerAdapter extends FragmentPagerAdapter
{
    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int inNumTabs)
    {
        super(fm);
        this.numOfTabs = inNumTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                return new WooliesListFragment();

            case 1:
                return new ColesListFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return numOfTabs;
    }
}
