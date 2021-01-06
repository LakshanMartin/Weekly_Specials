package curtin.edu.au.weeklyspecials.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import curtin.edu.au.weeklyspecials.Data.ColesListSingleton;
import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.R;


public class ColesListFragment extends Fragment
{
    //RETRIEVE shopping list
    private ColesListSingleton colesList = ColesListSingleton.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coles_list, container, false);

        //Initialise Total Cost value
        TextView txtVTotalCost = (TextView)view.findViewById(R.id.txtVTotalCost);
        String totalCost = getActivity().getResources().getString(
                R.string.total_cost, colesList.getTotalCost());
        txtVTotalCost.setText(totalCost);

        buildRecyclerView(view);

        return view;
    }

    private void buildRecyclerView(View view)
    {
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rvColesList);
        rv.setHasFixedSize(true);

        //Set default layout
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1,
                RecyclerView.VERTICAL, false));

        ColesListFragment.MyAdapter adapter = new ColesListFragment.MyAdapter(colesList.getShoppingList());
        rv.setAdapter(adapter);
    }

    // PRIVATE INNER CLASSES ---------------------------------------------------
    private class ItemDataVHolder extends RecyclerView.ViewHolder
    {
        private TextView txtVDesc, txtVCost, txtVQty;

        public ItemDataVHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_layout, parent, false));
            txtVDesc = (TextView)itemView.findViewById(R.id.txtVDesc);
            txtVCost = (TextView)itemView.findViewById(R.id.txtVCost);
            txtVQty = (TextView)itemView.findViewById(R.id.txtVQty);
        }

        public void bind(ItemData itemData, int position)
        {
            txtVDesc.setText(itemData.getDesc());
            txtVCost.setText(itemData.getCost());
            txtVQty.setText(itemData.getQty());
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<ColesListFragment.ItemDataVHolder>
    {
        private List<ItemData> itemList;

        public MyAdapter(List<ItemData> inList)
        {
            this.itemList = inList;
        }

        @NonNull
        @Override
        public ColesListFragment.ItemDataVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new ColesListFragment.ItemDataVHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ColesListFragment.ItemDataVHolder holder, int position)
        {
            holder.bind(itemList.get(position), position);
        }

        @Override
        public int getItemCount()
        {
            return itemList.size();
        }
    }
}