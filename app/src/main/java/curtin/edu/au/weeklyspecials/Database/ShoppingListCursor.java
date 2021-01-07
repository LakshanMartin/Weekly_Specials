package curtin.edu.au.weeklyspecials.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Database.ShoppingListSchema.ListTable;

public class ShoppingListCursor extends CursorWrapper
{
    public ShoppingListCursor(Cursor cursor)
    {
        super(cursor);
    }

    public ItemData getItem()
    {
        String desc = getString(getColumnIndex(ListTable.Cols.DESC));
        double cost = getDouble(getColumnIndex(ListTable.Cols.COST));
        int qty = getInt(getColumnIndex(ListTable.Cols.QTY));

        return new ItemData(desc, cost, qty);
    }
}
