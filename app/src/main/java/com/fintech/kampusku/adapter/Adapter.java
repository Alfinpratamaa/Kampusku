package com.fintech.kampusku.adapter;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.kampusku.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Cursor cursor;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String npm);
        void onEditClick(String npm);
        void onDeleteClick(String npm);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cursor == null) {
            return;
        }
        cursor.moveToPosition(position);

        String nama = cursor.getString(cursor.getColumnIndex("nama"));
        String npm = cursor.getString(cursor.getColumnIndex("npm"));

        holder.namaTv.setText(nama);
        holder.npmTv.setText(npm);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onEditClick(npm);
                    Log.d("MahasiswaAdapter", "Edit button clicked with npm: " + npm);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onDeleteClick(npm);
                    Log.d("MahasiswaAdapter", "delete button clicked with npm: " + npm);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(npm);
                    Log.d("MahasiswaAdapter", "Item clicked with npm: " + npm);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaTv, npmTv;
        ImageButton editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTv = itemView.findViewById(R.id.namaTv);
            npmTv = itemView.findViewById(R.id.npmTv);
            editButton = itemView.findViewById(R.id.editBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
