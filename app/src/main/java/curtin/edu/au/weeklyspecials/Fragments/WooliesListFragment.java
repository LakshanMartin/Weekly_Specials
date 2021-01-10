package curtin.edu.au.weeklyspecials.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import curtin.edu.au.weeklyspecials.Data.ItemData;
import curtin.edu.au.weeklyspecials.Data.WooliesListSingleton;
import curtin.edu.au.weeklyspecials.Database.ShoppingListDb;
import curtin.edu.au.weeklyspecials.R;
import curtin.edu.au.weeklyspecials.ShoppingListsActivity;


public class WooliesListFragment extends Fragment
{
    private TextView txtVTotalCost;
    private MyAdapter adapter;

    //RETRIEVE shopping list
    private WooliesListSingleton wooliesList = WooliesListSingleton.getInstance();
    private ShoppingListDb db = new ShoppingListDb();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_woolies_list, container, false);

        //Open database
        db.open(getActivity());

        //Initialise Total Cost value
        txtVTotalCost = (TextView)view.findViewById(R.id.txtVTotalCost);
        updateTotalCost();

        //Initialise Clear list button
        ImageButton clearList = (ImageButton)view.findViewById(R.id.imgBClearList);
        clearList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Clear database and existing list
                db.deleteWoolies();
                wooliesList.freshList();

                //Refresh activity with clean database table and fresh list
                getActivity().finish();

                Intent intent = new Intent(getActivity(), ShoppingListsActivity.class);
                intent.putExtra("LIST_ID", 0);

                startActivity(intent);
            }
        });

        buildRecyclerView(view);

        return view;
    }

    private void buildRecyclerView(View view)
    {
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rvWooliesList);
        rv.setHasFixedSize(true);

        //Set default layout
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1,
                RecyclerView.VERTICAL, false));

        adapter = new MyAdapter(wooliesList.getShoppingList());
        rv.setAdapter(adapter);
    }

    // PRIVATE INNER CLASSES ---------------------------------------------------
    private class ItemDataVHolder extends RecyclerView.ViewHolder
    {
        private TextView txtVDesc, txtVCost, txtVQty;
        private ImageButton imgBDelete;

        public ItemDataVHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_layout, parent, false));
            txtVDesc = (TextView)itemView.findViewById(R.id.txtVDesc);
            txtVCost = (TextView)itemView.findViewById(R.id.txtVCost);
            txtVQty = (TextView)itemView.findViewById(R.id.txtVQty);
            imgBDelete = (ImageButton)itemView.findViewById(R.id.imgBDelete);

            imgBDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int itemPosition = getAbsoluteAdapterPosition();
                    ItemData toDelete = wooliesList.getShoppingList().get(itemPosition);

                    //Remove from database
                    db.removeWooliesItem(toDelete);

                    //Remove from shopping list
                    wooliesList.removeItem(itemPosition);

                    //Refresh recycler view
                    adapter.notifyDataSetChanged();

                    //Reset Shopping list Total Cost
                    updateTotalCost();
                }
            });
        }

        public void bind(ItemData itemData, int position)
        {
            txtVDesc.setText(itemData.getDesc());
            txtVCost.setText(itemData.getCost());
            txtVQty.setText(itemData.getQty());
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<ItemDataVHolder>
    {
        private List<ItemData> itemList;

        public MyAdapter(List<ItemData> inList)
        {
            this.itemList = inList;
        }

        @NonNull
        @Override
        public ItemDataVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new ItemDataVHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemDataVHolder holder, int position)
        {
            holder.bind(itemList.get(position), position);
        }

        @Override
        public int getItemCount()
        {
            return itemList.size();
        }
    }

    private void updateTotalCost()
    {
        String totalCost = getActivity().getResources().getString(
                R.string.total_cost, wooliesList.getTotalCost());
        txtVTotalCost.setText(totalCost);
    }
}