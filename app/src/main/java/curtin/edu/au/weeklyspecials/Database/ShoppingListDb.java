package curtin.edu.au.weeklyspecials.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Database.ShoppingListSchema.ListTable;

public class ShoppingListDb
{
    private List<ItemData> wooliesList;
    private List<ItemData> colesList;
    private SQLiteDatabase db;

    public void open(Context context)
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

    public void removeWooliesItem(ItemData item)
    {
        db.execSQL("delete from " + ListTable.WOOLIES +
                " where " + ListTable.Cols.DESC + "='" + item.getDesc() + "'");
    }

    public void removeColesItem(ItemData item)
    {
        db.execSQL("delete from " + ListTable.COLES +
                " where " + ListTable.Cols.DESC + "='" + item.getDesc() + "'");
    }

    public void addColesItem(ItemData item)
    {
        ContentValues cv = new ContentValues();
        cv.put(ListTable.Cols.DESC, item.getDesc());
        cv.put(ListTable.Cols.COST, item.getCost());
        cv.put(ListTable.Cols.QTY, item.getQty());

        db.insert(ListTable.COLES, null, cv);
    }

    public Cursor fetchWoolies()
    {
        Cursor cursor = db.query(
                ListTable.WOOLIES,
                getColumns(),
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    public Cursor fetchColes()
    {
        Cursor cursor = db.query(
                ListTable.COLES,
                getColumns(),
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    private String[] getColumns()
    {
        String[] columns = new String[] {
                ListTable.Cols.DESC,
                ListTable.Cols.COST,
                ListTable.Cols.QTY };

        return columns;
    }

    public void deleteWoolies()
    {
        db.execSQL("delete from " + ListTable.WOOLIES);
    }

    public void deleteColes()
    {
        db.execSQL("delete from " + ListTable.COLES);
    }
}
