package curtin.edu.au.weeklyspecials.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Database.ShoppingListSchema.ListTable;

public class ShoppingListDb
{
    private List<ItemData> wooliesList;
    private List<ItemData> colesList;
    private SQLiteDatabase db;

    public void load(Context context)
    {
        //Open database
        this.db = new ShoppingListDbHelper(context.getApplicationContext())
                .getWritableDatabase();
    }

    public void addWooliesItem(ItemData item)
    {
        ContentValues cv = new ContentValues();
        cv.put(ListTable.Cols.DESC, item.getDesc());
        cv.put(ListTable.Cols.COST, item.getCost());
        cv.put(ListTable.Cols.QTY, item.getQty());

        db.insert(ListTable.WOOLIES, null, cv);
    }

    public void addColesItem(ItemData item)
    {
        ContentValues cv = new ContentValues();
        cv.put(ListTable.Cols.DESC, item.getDesc());
        cv.put(ListTable.Cols.COST, item.getCost());
        cv.put(ListTable.Cols.QTY, item.getQty());

        db.insert(ListTable.COLES, null, cv);
    }

    public void removeWooliesItem(ItemData item)
    {
        String[] whereValue = {item.getDesc()};

        db.delete(ListTable.WOOLIES,
                ListTable.Cols.DESC + " = ?", whereValue);
    }

    public void removeColesItem(ItemData item)
    {
        String[] whereValue = {item.getDesc()};

        db.delete(ListTable.COLES,
                ListTable.Cols.DESC + " = ?", whereValue);
    }

    public void updateWooliesItem(ItemData item)
    {
        ContentValues cv = new ContentValues();
        cv.put(ListTable.Cols.DESC, item.getDesc());
        cv.put(ListTable.Cols.COST, item.getCost());
        cv.put(ListTable.Cols.QTY, item.getQty());

        String[] whereValue = {item.getDesc()};

        db.update(ListTable.WOOLIES, cv,
                ListTable.Cols.DESC + " = ?", whereValue);
    }

    public void updateColesItem(ItemData item)
    {
        ContentValues cv = new ContentValues();
        cv.put(ListTable.Cols.DESC, item.getDesc());
        cv.put(ListTable.Cols.COST, item.getCost());
        cv.put(ListTable.Cols.QTY, item.getQty());

        String[] whereValue = {item.getDesc()};

        db.update(ListTable.COLES, cv,
                ListTable.Cols.DESC + " = ?", whereValue);
    }


}
