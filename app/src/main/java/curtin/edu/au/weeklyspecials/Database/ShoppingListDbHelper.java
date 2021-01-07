package curtin.edu.au.weeklyspecials.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import curtin.edu.au.weeklyspecials.Database.ShoppingListSchema.ListTable;

public class ShoppingListDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "shoppingLists.db";

    public ShoppingListDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Create Woolies Table
        db.execSQL("CREATE TABLE " + ListTable.WOOLIES + "(" +
                ListTable.Cols.DESC + " TEXT, " +
                ListTable.Cols.COST + " REAL, " +
                ListTable.Cols.QTY + " INTEGER)");

        //Create Coles Table
        db.execSQL("CREATE TABLE " + ListTable.COLES + "(" +
                ListTable.Cols.DESC + " TEXT, " +
                ListTable.Cols.COST + " REAL, " +
                ListTable.Cols.QTY + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {

    }
}
