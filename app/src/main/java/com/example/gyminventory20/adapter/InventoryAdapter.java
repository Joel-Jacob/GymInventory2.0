package com.example.gyminventory20.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gyminventory20.R;
import com.example.gyminventory20.model.Inventory;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.CustomInventoryHolder> {
    private List<Inventory> inventoryList;
    private InventoryDelegate inventoryDelegate;

    public InventoryAdapter(List<Inventory> inventoryList, InventoryDelegate inventoryDelegate) {
        this.inventoryList = inventoryList;
        this.inventoryDelegate = inventoryDelegate;
    }

    public interface InventoryDelegate{
        void viewItem(Inventory inventory);
    }

    @NonNull
    @Override
    public CustomInventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gym_inventory_layout, parent, false);

        return new CustomInventoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomInventoryHolder holder, final int position) {
        holder.gymItemTitle.setText(inventoryList.get(position).getItemName());
        holder.gymItemQuantity.setText(inventoryList.get(position).getItemQuantity()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryDelegate.viewItem(inventoryList.get(position));
                Log.d("TagX","selected: "+inventoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public class CustomInventoryHolder extends RecyclerView.ViewHolder {
        public TextView gymItemTitle;
        public TextView gymItemQuantity;

        public CustomInventoryHolder(@NonNull View itemView) {
            super(itemView);

            gymItemTitle = itemView.findViewById(R.id.gym_inventory_title_textView);
            gymItemQuantity = itemView.findViewById(R.id.gym_inventory_count_textView);
        }
    }
}
