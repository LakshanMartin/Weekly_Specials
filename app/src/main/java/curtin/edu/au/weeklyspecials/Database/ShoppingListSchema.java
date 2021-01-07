package curtin.edu.au.weeklyspecials.Database;

public class ShoppingListSchema
{
    public static class ListTable
    {
        public static final String WOOLIES = "woolies_list"; //Woolies Table name
        public static final String COLES = "coles_list"; //Coles Table name

        public static class Cols //Column names
        {
            public static final String DESC = "description";
            public static final String COST = "cost";
            public static final String QTY = "qty";
        }
    }
}
