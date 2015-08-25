package com.binary_eclipse.unlocator.app.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.binary_eclipse.unlocator.app.R;
import com.binary_eclipse.unlocator.app.model.Service;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andras on 15/07/06.
 */
public class NetflixViewHolder extends RecyclerView.ViewHolder {

    public interface Listener {
        void clicked(Service service, String item);
    }

    private Listener listener;

    @OnClick(R.id.region_label)
    public void clicked() {
        listener.clicked(Service.NETFLIX, textView.getText().toString());
    }

    @Bind(R.id.region_label)
    TextView textView;

    public NetflixViewHolder(View view, Listener listener) {
        super(view);
        this.listener = listener;
        ButterKnife.bind(this, view);
    }

    public void bindData(String item) {
        textView.setText(item);
    }
}
