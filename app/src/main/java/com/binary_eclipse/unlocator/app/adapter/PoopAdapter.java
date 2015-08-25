package com.binary_eclipse.unlocator.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binary_eclipse.unlocator.app.R;
import com.binary_eclipse.unlocator.app.adapter.viewholder.ApiKeyViewHolder;
import com.binary_eclipse.unlocator.app.adapter.viewholder.NetflixViewHolder;
import com.binary_eclipse.unlocator.app.model.Service;

import java.util.List;

public class PoopAdapter extends RecyclerView.Adapter implements NetflixViewHolder.Listener {
    private static final int NETFLIX_SECTION = 1;
    private List<String> netflixRegions;
    private Listener listener;

    public interface Listener {
        void clicked(Service service, String item);
    }

    public PoopAdapter(List<String> netflixRegions, Listener listener) {
        this.netflixRegions = netflixRegions;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case NETFLIX_SECTION:
                view = from.inflate(R.layout.netflix_section, parent, false);
                return new NetflixViewHolder(view, this);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NetflixViewHolder) holder).bindData(getItem(position));

    }

    private String getItem(int position) {
        return netflixRegions.get(position);
    }

    @Override
    public int getItemCount() {
        return netflixRegions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return NETFLIX_SECTION;
    }

    @Override
    public void clicked(Service service, String item) {
        listener.clicked(service, item);
    }
}
