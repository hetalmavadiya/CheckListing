package com.example.keepnotes.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepnotes.Model.ChecklistItem;
import com.example.keepnotes.R;
import com.example.keepnotes.helper.SessionManage;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder> {

    private List<ChecklistItem> itemList;
    private SessionManage sessionManage;
    private OnCheckedChangeListener listener;

    public ChecklistAdapter(List<ChecklistItem> itemList, SessionManage sessionManage) {
        this.itemList = itemList;
        this.sessionManage = sessionManage;
    }

    public List<ChecklistItem> getItemList() {
        return itemList;
    }

    @NonNull
    @Override
    public ChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_checkbox, parent, false);
        return new ChecklistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistViewHolder holder, int position) {
        ChecklistItem item = itemList.get(position);

        holder.checkBox.setChecked(item.isChecked());

        // Remove the text change listener before setting the text
        holder.editText.removeTextChangedListener(holder.textWatcher);
        holder.editText.setText(item.getItemName());
        holder.editText.setTag(position); // Set the position as the tag to identify the EditText

        // Create a new TextWatcher and set it to the EditText
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int adapterPosition = (int) holder.editText.getTag(); // Get the position from the tag
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    ChecklistItem item = itemList.get(adapterPosition);
                    item.setItemName(s.toString());
                    if (listener != null) {
                        listener.onItemNameChanged(item);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        holder.editText.addTextChangedListener(holder.textWatcher);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.toggleChecked();
                if (listener != null) {
                    listener.onCheckedChanged(item);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isChecked()) {
                    sessionManage.removeCheckedItem(item);
                } else {
                    sessionManage.removeUncheckedItem(item);
                }

                itemList.remove(item);
                notifyDataSetChanged();

                // Call onDeleteItem method in the listener to notify MainActivity of item deletion
                if (listener != null) {
                    listener.onDeleteItem();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(ChecklistItem item);

        void onItemNameChanged(ChecklistItem item);

        void onDeleteItem();
    }

    public static class ChecklistViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private EditText editText;
        private ImageView deleteButton;

        private TextWatcher textWatcher;

        public ChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            editText = itemView.findViewById(R.id.editText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
