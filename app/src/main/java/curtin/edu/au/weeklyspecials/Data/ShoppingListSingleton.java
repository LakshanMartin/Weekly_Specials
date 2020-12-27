package curtin.edu.au.weeklyspecials.Data;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListSingleton
{
    private static ShoppingListSingleton instance = null;
    private List<String> shoppingList;

    private ShoppingListSingleton()
    {
        shoppingList = new ArrayList<>();
    }

    public static ShoppingListSingleton getInstance()
    {
        if(instance == null)
        {
            instance = new ShoppingListSingleton();
        }

        return instance;
    }

    public void addItem(String newItem)
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

    public List<String> getShoppingList()
    {
        return shoppingList;
    }
}
