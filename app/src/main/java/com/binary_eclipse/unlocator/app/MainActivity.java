package com.binary_eclipse.unlocator.app;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.binary_eclipse.unlocator.app.adapter.PoopAdapter;
import com.binary_eclipse.unlocator.app.adapter.viewholder.ApiKeyViewHolder;
import com.binary_eclipse.unlocator.app.model.RegionResponse;
import com.binary_eclipse.unlocator.app.model.Service;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements PoopAdapter.Listener, ApiKeyViewHolder.Listener {

    List<String> netflixRegions;

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    ApiKeyViewHolder holder;
    private String apiKey;
    private PoopAdapter adapter;

    @Bind(R.id.poop_list)
    RecyclerView poopList;

    @Bind(android.R.id.content)
    public View root;

    @Bind(R.id.progress_view)
    View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        poopList.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));

        holder = new ApiKeyViewHolder(root, this);

        refreshRegions();
    }

    private void refreshRegions() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                setProgress(true);
            }

            @Override
            protected Void doInBackground(Void... params) {
                getRegions();
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

    private void getRegions() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("unlocator.com")
                .addPathSegment("tool")
                .addPathSegment("regions.xml")
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
            RegionResponse response1 = parseBody(body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RegionResponse parseBody(String input) {
        RegionResponse response = null;
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(input);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
//            Log.e("sss", jsonPrettyPrintString);
            Gson gson = new Gson();
            response = gson.fromJson(jsonPrettyPrintString, RegionResponse.class);
            if (response != null) {
                netflixRegions = response.getChannels().getNetflix().getCountry();
            }

        } catch (JSONException je) {
            System.out.println(je.toString());
        }
        return response;
    }

    private void initViews() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            apiKey = wifiInfo.getSSID();
        } else {
            apiKey = "no wifi...";
        }
        holder.bindData(apiKey);
        adapter = new PoopAdapter(netflixRegions, this);
        poopList.setAdapter(adapter);
    }

    private void updateRegion(final Service service, final String item) {
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
//                        .host("unlocator.com")
                        .host("unlo.it")
//                        .addPathSegment("tool")
//                        .addPathSegment("api.php")
                        .addPathSegment("59cf0c0244f77c3")
//                        .addQueryParameter("api_key", apiKey)
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
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    @Override
    public void clicked(final Service service, final String item) {
        updateRegion(service, item);
    }

    @Override
    public void updateText(String text) {
        apiKey = text;
    }

    void setProgress(boolean progress) {
        poopList.setVisibility(progress ? View.GONE : View.VISIBLE);
        progressView.setVisibility(progress ? View.VISIBLE : View.GONE);
    }
}
