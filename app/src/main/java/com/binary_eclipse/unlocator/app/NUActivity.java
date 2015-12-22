package com.binary_eclipse.unlocator.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.binary_eclipse.unlocator.app.adapter.PoopAdapter;
import com.binary_eclipse.unlocator.app.adapter.viewholder.ApiKeyViewHolder;
import com.binary_eclipse.unlocator.app.model.Service;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;

public abstract class NUActivity extends Activity implements PoopAdapter.Listener, ApiKeyViewHolder.Listener {

    public static final String POOP = "PUT_YOUR_SHORT_URL_KEY_HERE";

    List<String> netflixRegions;

    ApiKeyViewHolder holder;
    protected String apiKey;

    @Bind(android.R.id.content)
    public View root;

    @Bind(R.id.progress_view)
    View progressView;

    protected void refreshRegions() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                setProgress(true);
            }

            @Override
            protected Void doInBackground(Void... params) {
                netflixRegions = Unlocator.getRegions();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setProgress(false);
                initViews();
            }
        }.execute();
    }

    protected abstract void initViews();

    protected void setProgress(boolean progress) {
        setScreenVisibility(progress);
        progressView.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    protected abstract void setScreenVisibility(boolean progress);

    protected void updateRegion(final Service service, final String item) {
        final String string = apiKey;
        if (POOP.equals(string) || string.isEmpty()) {
            new AlertDialog.Builder(this).setTitle("API Key!").setMessage("Go to unlocator.com and " +
                    "copy the key part of the shortened URL (http://unlo.it/<key>) of the \"Your Private API Key and URL\" " +
                    "into unlocator_api_url_key in strings.xml").setNegativeButton("k...", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();
        } else {
            new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    setProgress(true);
                }

                @Override
                protected String doInBackground(Void... params) {
                    HttpUrl url = new HttpUrl.Builder()
                            .scheme("http")
                            .host("unlo.it")
                            .addPathSegment(string)
                            .addQueryParameter("channel", service.name().toLowerCase())
                            .addQueryParameter("country", item)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .get()
                            .url(url)
                            .build();
                    Call call = client.newCall(request);

                    Response response = null;
                    try {
                        response = call.execute();
                        ResponseBody body = response.body();
                        return body.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    setProgress(false);
                    super.onPostExecute(s);
                    Toast.makeText(NUActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }.execute();
        }
    }

    @Override
    public void updateText(String text) {
        SharedPreferences prefs = this.getSharedPreferences(
                "com.binary_eclipse.unlocator.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (null != editor) {
            editor.putString("API_KEY", text);
            editor.apply();
        }
        apiKey = text;
    }

    @Override
    public void clicked(final Service service, final String item) {
        updateRegion(service, item);
    }
}
