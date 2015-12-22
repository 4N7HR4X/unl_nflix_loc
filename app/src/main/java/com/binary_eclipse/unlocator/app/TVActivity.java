package com.binary_eclipse.unlocator.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.binary_eclipse.unlocator.app.adapter.viewholder.ApiKeyViewHolder;

import butterknife.ButterKnife;

public class TVActivity extends NUActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        poopList.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
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
    }

    @Override
    protected void setScreenVisibility(boolean progress) {

    }
}
