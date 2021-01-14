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

    public double getCost()
    {
        return cost;
        //return Double.toString(cost);
    }

    public int getQty()
    {
        return qty;
        //return Integer.toString(qty);
    }
}
