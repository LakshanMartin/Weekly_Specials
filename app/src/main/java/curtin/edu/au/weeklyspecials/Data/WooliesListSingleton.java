package curtin.edu.au.weeklyspecials.Data;

import java.util.ArrayList;
import java.util.List;

public class WooliesListSingleton
{
    private static WooliesListSingleton instance = null;
    private List<ItemData> shoppingList;
    private double totalCost;

    private WooliesListSingleton()
    {
        shoppingList = new ArrayList<>();
        this.totalCost = 0.0;
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
        double newItemCost, newItemQty;

        shoppingList.add(newItem);

        //Total cost calculations
        newItemCost = Double.parseDouble(newItem.getCost());
        newItemQty = Double.parseDouble(newItem.getQty());
        this.totalCost += newItemCost * newItemQty;
    }

    public void removeItem(int oldItemPosition)
    {
        ItemData oldItem;
        double oldItemCost, oldItemQty;

        oldItem = shoppingList.get(oldItemPosition);
        oldItemCost = Double.parseDouble(oldItem.getCost());
        oldItemQty = Double.parseDouble(oldItem.getQty());

        this.totalCost -= oldItemCost * oldItemQty;

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

    public double getTotalCost()
    {
        return totalCost;
    }
}
