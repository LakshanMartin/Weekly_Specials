package curtin.edu.au.weeklyspecials.Data;

import java.util.ArrayList;
import java.util.List;

public class ColesListSingleton
{
    private static ColesListSingleton instance = null;
    private List<ItemData> shoppingList;
    private double totalCost;
    private ItemData updatedItem;

    private ColesListSingleton()
    {
        freshList();
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
        double newItemCost, newItemQty;

        shoppingList.add(newItem);

        //Total cost calculations
        //newItemCost = Double.parseDouble(newItem.getCost());
        //newItemQty = Double.parseDouble(newItem.getQty());

        newItemCost = newItem.getCost();
        newItemQty = newItem.getQty();
        this.totalCost += newItemCost * newItemQty;
    }

    public void removeItem(int oldItemPosition)
    {
        ItemData oldItem;
        double oldItemCost, oldItemQty;

        oldItem = shoppingList.get(oldItemPosition);
        //oldItemCost = Double.parseDouble(oldItem.getCost());
        //oldItemQty = Double.parseDouble(oldItem.getQty());

        oldItemCost = oldItem.getCost();
        oldItemQty = oldItem.getQty();

        this.totalCost -= oldItemCost * oldItemQty;

        shoppingList.remove(oldItemPosition);
    }

    public void editItem(String desc, String cost, String qty)
    {
        updatedItem = new ItemData(desc, Double.parseDouble(cost), Integer.parseInt(qty));
    }

    public void updateItem(int itemPosition)
    {
        shoppingList.set(itemPosition, updatedItem);
    }

    public void freshList()
    {
        shoppingList = new ArrayList<>();
        this.totalCost = 0.0;
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

    public ItemData getUpdatedItem()
    {
        return updatedItem;
    }

    public double getTotalCost()
    {
        return totalCost;
    }
}
