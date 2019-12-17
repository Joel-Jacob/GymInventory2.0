package com.example.gyminventory20.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import com.example.gyminventory20.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CustomItemHolder> {
    private List<String> itemList;
    private ItemDelegate itemDelegate;

    public ItemAdapter(List<String> itemList, ItemDelegate itemDelegate) {
        this.itemList = itemList;
        this.itemDelegate = itemDelegate;
    }

    public interface ItemDelegate{

        void itemSelected(String selectedItem);
    }

    @NonNull
    @Override
    public ItemAdapter.CustomItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gym_item_layout, parent, false);

        return new CustomItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.CustomItemHolder holder, final int position) {
        holder.gymItemTitle.setText(itemList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDelegate.itemSelected(itemList.get(position));
                Log.d("TagX","selected: "+itemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CustomItemHolder extends RecyclerView.ViewHolder {
        public TextView gymItemTitle;

        public CustomItemHolder(@NonNull View itemView) {
            super(itemView);

            gymItemTitle = itemView.findViewById(R.id.gym_item_title_textView);
        }
    }
}
