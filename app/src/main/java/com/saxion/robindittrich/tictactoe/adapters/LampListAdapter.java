package com.saxion.robindittrich.tictactoe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saxion.robindittrich.tictactoe.R;
import com.saxion.robindittrich.tictactoe.managers.HueManager;

public class LampListAdapter extends RecyclerView.Adapter<LampListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public LampListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idTextView.setText("id: " + String.valueOf(HueManager.bridge.getLights().get(position).getId()));
        holder.nameTextView.setText("Name: " + HueManager.bridge.getLights().get(position).getName());

    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.light_layout, parent, false);
        return new ViewHolder(view);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return HueManager.bridge.getLights().size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idTextView;
        TextView nameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.tvID);
            nameTextView = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
