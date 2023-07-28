package com.example.keepnotes.Model;

public class ChecklistItem {
    private String itemName;
    private boolean isChecked;

    public ChecklistItem(String itemName, boolean isChecked) {
        this.itemName = itemName;
        this.isChecked = isChecked;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    // New method to toggle the checked state
    public void toggleChecked() {
        isChecked = !isChecked;
    }
}
