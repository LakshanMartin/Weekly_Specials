package curtin.edu.au.weeklyspecials.Data;

public class ItemData
{
    private String desc;
    private double cost;
    private int qty;

    //CONSTRUCTOR
    public ItemData(String inDesc, double inCost, int inQty)
    {
        this.desc = inDesc;
        this.cost = inCost;
        this.qty = inQty;
    }

    //GETTERS
    public String getDesc()
    {
        return desc;
    }

    public String getCost()
    {
        return Double.toString(cost);
    }

    public String getQty()
    {
        return Integer.toString(qty);
    }
}
