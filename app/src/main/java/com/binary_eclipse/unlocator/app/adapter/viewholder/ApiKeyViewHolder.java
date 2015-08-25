package com.binary_eclipse.unlocator.app.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import com.binary_eclipse.unlocator.app.R;

public class ApiKeyViewHolder extends RecyclerView.ViewHolder {
    public interface Listener {
        void updateText(String text) ;
    }

    public boolean hidden = true;

    private Listener listener;


    @OnClick(R.id.api_key_thingy)
    public void thingy() {
        setVisibility(!hidden);
    }

    @Bind(R.id.api_key_input)
    public EditText input;

    @OnTextChanged(R.id.api_key_input)
    public void textChanged(CharSequence text) {
        listener.updateText(text.toString());
    }


    public ApiKeyViewHolder(View view, Listener listener) {
        super(view);
        this.listener = listener;
        ButterKnife.bind(this, view);

        setVisibility(hidden);

    }

    private void setVisibility(boolean hidden) {
        input.setVisibility(hidden ? View.GONE : View.VISIBLE);
        this.hidden = hidden;
    }

    public void bindData(String apiKey) {
        input.setText(apiKey);
    }
}
