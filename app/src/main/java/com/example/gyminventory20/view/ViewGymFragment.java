package com.example.gyminventory20.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gyminventory20.R;
import com.example.gyminventory20.database.InventoryDatabaseHelper;
import com.example.gyminventory20.model.Inventory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewGymFragment extends Fragment {

    private ItemFragmentListener fragmentListener;
    private InventoryDatabaseHelper itemDatabase;

    private int quantity = 0;

    @BindView(R.id.item_title_fragment_textView)
    public TextView itemTitleTextView;

    @BindView(R.id.item_fragment_editText)
    public EditText itemEditText;

    @BindView(R.id.save_button_fragment)
    public Button saveButton;

    public interface ItemFragmentListener{
        void makeToast();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gym_fragment_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        String gymItem = getArguments().getString(MainActivity.return_key);

        itemTitleTextView.setText(gymItem);

        itemDatabase = new InventoryDatabaseHelper(this.getContext(), "",null,1);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemDatabase.insertItems(new Inventory(itemTitleTextView.getText().toString(), Integer.parseInt(itemEditText.getText().toString())));

                itemEditText.setText("");

                fragmentListener.makeToast();
            }
        });

    }

//    @OnClick(R.id.save_button_fragment)
//    public void saveQuantity(View view){
//
//        itemDatabase = new InventoryDatabaseHelper(this.getContext(), "",null,1);
//
//        Log.d("TAG_X", Integer.parseInt(itemEditText.getText().toString())+"");
//        itemDatabase.insertItems(new Inventory(itemTitleTextView.getText().toString(), Integer.parseInt(itemEditText.getText().toString())));
//
//        itemEditText.setText("");
//
//        fragmentListener.makeToast();
//
//        getActivity().getSupportFragmentManager().popBackStack();
//
//    }

    @OnClick(R.id.close_button_fragment)
    public void closeFragment(View view){
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof MainActivity){
            this.fragmentListener = (MainActivity)context;
        }
    }
}
