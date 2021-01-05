package curtin.edu.au.weeklyspecials.Data;

import java.util.ArrayList;
import java.util.List;

public class ColesListSingleton
{
    private static ColesListSingleton instance = null;
    private List<ItemData> shoppingList;

    private ColesListSingleton()
    {
        shoppingList = new ArrayList<>();
    }

    public static ColesListSingleton getInstance()
    {
        if(instance == null)
        {
            instance = new ColesListSingleton();
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
