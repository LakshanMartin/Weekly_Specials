package curtin.edu.au.weeklyspecials.Data;

import java.util.ArrayList;
import java.util.List;

public class WooliesListSingleton
{
    private static WooliesListSingleton instance = null;
    private List<ItemData> shoppingList;

    private WooliesListSingleton()
    {
        shoppingList = new ArrayList<>();
    }

    public static WooliesListSingleton getInstance()
    {
        if(instance == null)
        {
            instance = new WooliesListSingleton();
        }

        return instance;
    }

    public void addItem(ItemData newItem)
    {
        shoppingList.add(newItem);
    }

    public void removeItem(int oldItemPosition)
    {
        shoppingList.remove(oldItemPosition);
    }

    public boolean isEmpty()
    {
        boolean isEmpty = false;

        if(shoppingList.isEmpty())
        {
            isEmpty = true;
        }

        return isEmpty;
    }

    public List<ItemData> getShoppingList()
    {
        return shoppingList;
    }
}
