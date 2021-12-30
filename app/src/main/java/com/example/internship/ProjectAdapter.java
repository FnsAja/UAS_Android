package com.example.internship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewProcessHolder> {
    Context context;
    private ArrayList<Model> item;
    onListListener mOnListListener;

    public ProjectAdapter(Context context, ArrayList<Model> item, onListListener onListListener) {
        this.context = context;
        this.item = item;
        this.mOnListListener = onListListener;
    }

    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_project, parent, false);
        ViewProcessHolder processHolder = new ViewProcessHolder(view, mOnListListener);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(ViewProcessHolder holder, int position) {

        final Model data = item.get(position);
        holder.nama_data.setText(data.getNamaProject());
        holder.jumlah_data.setText(data.getJumlahMember());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nama_data, jumlah_data;
        onListListener onListListener;
        public ViewProcessHolder(@NonNull View itemView, onListListener onListListener) {
            super(itemView);
            nama_data = (TextView) itemView.findViewById(R.id.name);
            jumlah_data = (TextView) itemView.findViewById(R.id.count);
            this.onListListener = onListListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListListener.onListClick(getAdapterPosition());
        }
    }

    public interface onListListener{
        void onListClick(int position);
    }
}
