package com.binary_eclipse.unlocator.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.binary_eclipse.unlocator.app.adapter.PoopAdapter;
import com.binary_eclipse.unlocator.app.adapter.viewholder.ApiKeyViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends NUActivity {

    protected PoopAdapter adapter;

    @Bind(R.id.poop_list)
    RecyclerView poopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        poopList.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        holder = new ApiKeyViewHolder(root, this);

        refreshRegions();
    }

    protected void initViews() {
        SharedPreferences prefs = this.getSharedPreferences(
                "com.binary_eclipse.unlocator.app", Context.MODE_PRIVATE);
        if (null != prefs) {
            apiKey = prefs.getString("API_KEY", POOP);
        }
        holder.bindData(apiKey);
        adapter = new PoopAdapter(netflixRegions, this);
        poopList.setAdapter(adapter);
    }

    @Override
    protected void setScreenVisibility(boolean progress) {
        poopList.setVisibility(progress ? View.GONE : View.VISIBLE);
    }
}
