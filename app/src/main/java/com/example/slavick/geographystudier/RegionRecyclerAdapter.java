package com.example.slavick.geographystudier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RegionRecyclerAdapter extends RecyclerView.Adapter<RegionRecyclerAdapter.ViewHolder> {
    public Context context;
    public List<String> regions;
    public CountryRecyclerAdapter.OnRecyclerItemClick onRecyclerItemClick;

    public RegionRecyclerAdapter(Context context, List<String> regions, CountryRecyclerAdapter.OnRecyclerItemClick onRecyclerItemClick) {
        this.context = context;
        this.regions = regions;
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String region = regions.get(position);
        holder.name.setText(region);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
