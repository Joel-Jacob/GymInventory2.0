package com.example.gyminventory20.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.gyminventory20.R;
import com.example.gyminventory20.adapter.InventoryAdapter;
import com.example.gyminventory20.adapter.ItemAdapter;
import com.example.gyminventory20.database.InventoryDatabaseHelper;
import com.example.gyminventory20.model.Inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.gyminventory20.util.Logger.logError;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemDelegate, InventoryAdapter.InventoryDelegate, ViewGymFragment.ItemFragmentListener {

    private List<String> itemList = new ArrayList<>();
    private List<Inventory> inventoryList = new ArrayList<>();

    public static String return_key = "return_key";

    @BindView(R.id.itemRecyclerView)
    RecyclerView itemRecyclerView;

    @BindView(R.id.inventoryRecyclerView)
    RecyclerView inventoryRecyclerView;

    private InventoryDatabaseHelper itemDatabase = new InventoryDatabaseHelper(this, "",null,1);

    private ViewGymFragment gymFragment = new ViewGymFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        itemList.add("Dumbbells");
        itemList.add("Treadmills");
        itemList.add("Yoga Mats");

        setItemRecyclerView();
        setInventoryRecyclerView();

        RecyclerView.ItemDecoration decorator = new DividerItemDecoration(this,RecyclerView.VERTICAL);
        inventoryRecyclerView.addItemDecoration(decorator);
        itemRecyclerView.addItemDecoration(decorator);
    }

    public void setInventoryRecyclerView(){
        //read from DB
        Cursor items = itemDatabase.retreiveReceipts();
        items.moveToFirst();

        while (items.moveToNext()){
            int itemId = items.getInt(items.getColumnIndex(InventoryDatabaseHelper.COLUMN_ITEM_ID));
            String itemTitle = items.getString(items.getColumnIndex((InventoryDatabaseHelper.COLUMN_ITEM_TITLE)));
            int itemQuantity = items.getInt(items.getColumnIndex((InventoryDatabaseHelper.COLUMN_ITEM_QUANTITY)));

            Inventory inventoryObj = new Inventory(itemTitle, itemQuantity);
            inventoryList.add(inventoryObj);

            Log.d("TAG_Y", itemTitle + " " + itemQuantity + " " + itemId);




        }
        Log.d("TAG_Z", "reading from db");

        inventoryRecyclerView.setAdapter(new InventoryAdapter(inventoryList, this));
        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        inventoryRecyclerView.setLayoutManager(layoutMgr);

        for(int i=0;i<inventoryList.size();i++){
            Log.d("TAG_Z", inventoryList.size()+ " i: "+i);

            //
            //inventoryList.get(i).getItemName()+" "+inventoryList.get(i).getItemID()
            //
        }

    }

    public void setItemRecyclerView(){
        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);

        itemRecyclerView.setLayoutManager(layoutMgr);
        itemRecyclerView.setAdapter(new ItemAdapter(itemList,this));
    }


    @Override
    public void itemSelected(String selectedItem) {
        Bundle receiptBundle = new Bundle();
        receiptBundle.putString(return_key, selectedItem);

        Log.d("TagX","selected: "+selectedItem);

        gymFragment.setArguments(receiptBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frameLayout, gymFragment)
                .addToBackStack(gymFragment.getTag())
                .commit();
    }

    @Override
    public void makeToast() {
        //back from fragment
        getSupportFragmentManager().popBackStack();
        setInventoryRecyclerView();
    }

    @Override
    public void viewItem(Inventory inventory) {
        //clicked second recycler view item
    }


}
