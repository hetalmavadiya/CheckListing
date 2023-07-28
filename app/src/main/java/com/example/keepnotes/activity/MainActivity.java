package com.example.keepnotes.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepnotes.Model.ChecklistItem;
import com.example.keepnotes.R;
import com.example.keepnotes.adapter.ChecklistAdapter;
import com.example.keepnotes.helper.SessionManage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ChecklistItem> uncheckedItemsList = new ArrayList<>();
    private List<ChecklistItem> checkedItemsList = new ArrayList<>();
    private ChecklistAdapter uncheckedItemsAdapter;
    private ChecklistAdapter checkedItemsAdapter;

    private SessionManage sessionManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         sessionManage = new SessionManage(this);

        uncheckedItemsAdapter = new ChecklistAdapter(uncheckedItemsList,sessionManage);
        checkedItemsAdapter = new ChecklistAdapter(checkedItemsList,sessionManage);

        RecyclerView recyclerViewUnchecked = findViewById(R.id.recycler_view_unchecked);
        recyclerViewUnchecked.setLayoutManager(new LinearLayoutManager(this));
        uncheckedItemsAdapter = new ChecklistAdapter(uncheckedItemsList,sessionManage);
        recyclerViewUnchecked.setAdapter(uncheckedItemsAdapter);

        RecyclerView recyclerViewChecked = findViewById(R.id.recycler_view_checked);
        recyclerViewChecked.setLayoutManager(new LinearLayoutManager(this));
        checkedItemsAdapter = new ChecklistAdapter(checkedItemsList,sessionManage);
        recyclerViewChecked.setAdapter(checkedItemsAdapter);

        ImageView btnAdd = findViewById(R.id.btnAdd);
        final EditText editTextNewItem = findViewById(R.id.editTextNewItem);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(editTextNewItem);
            }
        });

        uncheckedItemsAdapter.setOnCheckedChangeListener(new ChecklistAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChecklistItem item) {
                uncheckedItemsList.remove(item);
                item.setChecked(true);
                checkedItemsList.add(new ChecklistItem(item.getItemName(), true));
                uncheckedItemsAdapter.notifyDataSetChanged();
                checkedItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemNameChanged(ChecklistItem item) {
            }
        });

        checkedItemsAdapter.setOnCheckedChangeListener(new ChecklistAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChecklistItem item) {
                checkedItemsList.remove(item);
                item.setChecked(false);
                uncheckedItemsList.add(new ChecklistItem(item.getItemName(), false));
                checkedItemsAdapter.notifyDataSetChanged();
                uncheckedItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemNameChanged(ChecklistItem item) {
            }
        });
    }

    private void addItem(EditText editTextNewItem) {
        String newItemName = editTextNewItem.getText().toString().trim();
        if (!newItemName.isEmpty()) {
            ChecklistItem newItem = new ChecklistItem(newItemName, false);
            uncheckedItemsList.add(newItem);
            uncheckedItemsAdapter.notifyItemInserted(uncheckedItemsList.size() - 1);
            editTextNewItem.setText("");
            editTextNewItem.clearFocus();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveDataToSession();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromSession();
    }

    private void saveDataToSession() {
        List<ChecklistItem> uncheckedItems = uncheckedItemsAdapter.getItemList();
        List<ChecklistItem> checkedItems = checkedItemsAdapter.getItemList();
        sessionManage.saveUncheckedItems(uncheckedItems);
        sessionManage.saveCheckedItems(checkedItems);
    }

    private void loadDataFromSession() {
        List<ChecklistItem> savedUncheckedItems = sessionManage.loadUncheckedItems();
        if (savedUncheckedItems != null) {
            uncheckedItemsList.clear();
            uncheckedItemsList.addAll(savedUncheckedItems);
            uncheckedItemsAdapter.notifyDataSetChanged();
        }

        List<ChecklistItem> savedCheckedItems = sessionManage.loadCheckedItems();
        if (savedCheckedItems != null) {
            checkedItemsList.clear();
            checkedItemsList.addAll(savedCheckedItems);
            checkedItemsAdapter.notifyDataSetChanged();
        }
    }
}
