package com.example.keepnotes.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.keepnotes.Model.ChecklistItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class SessionManage {

    private static final String SHARED_PREFS_KEY = "checklist_prefs";
    private static final String UNCHECKED_ITEMS_KEY = "unchecked_items";
    private static final String CHECKED_ITEMS_KEY = "checked_items";

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SessionManage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveUncheckedItems(List<ChecklistItem> uncheckedItems) {
        String uncheckedItemsJson = gson.toJson(uncheckedItems);
        sharedPreferences.edit().putString(UNCHECKED_ITEMS_KEY, uncheckedItemsJson).apply();
    }

    public List<ChecklistItem> loadUncheckedItems() {
        String uncheckedItemsJson = sharedPreferences.getString(UNCHECKED_ITEMS_KEY, "");
        Type itemType = new TypeToken<List<ChecklistItem>>() {}.getType();
        return gson.fromJson(uncheckedItemsJson, itemType);
    }

    public void saveCheckedItems(List<ChecklistItem> checkedItems) {
        String checkedItemsJson = gson.toJson(checkedItems);
        sharedPreferences.edit().putString(CHECKED_ITEMS_KEY, checkedItemsJson).apply();
    }

    public List<ChecklistItem> loadCheckedItems() {
        String checkedItemsJson = sharedPreferences.getString(CHECKED_ITEMS_KEY, "");
        Type itemType = new TypeToken<List<ChecklistItem>>() {}.getType();
        return gson.fromJson(checkedItemsJson, itemType);
    }

    public void clearSession() {
        sharedPreferences.edit().clear().apply();
    }





}
