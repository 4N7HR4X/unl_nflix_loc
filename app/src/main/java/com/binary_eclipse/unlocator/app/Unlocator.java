package com.binary_eclipse.unlocator.app;

import com.binary_eclipse.unlocator.app.model.RegionResponse;
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
import java.util.ArrayList;
import java.util.List;

public class Unlocator {

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static List<String> getRegions() {
        List<String> regions = new ArrayList<>();
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

        Response response;
        try {
            response = call.execute();
            ResponseBody body = response.body();
            RegionResponse response1 = parseBody(body.string());
            regions = response1.getChannels().getNetflix().getCountry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return regions;
    }

    private static RegionResponse parseBody(String input) {
        RegionResponse response = null;
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(input);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            Gson gson = new Gson();
            response = gson.fromJson(jsonPrettyPrintString, RegionResponse.class);

        } catch (JSONException je) {
            System.out.println(je.toString());
        }
        return response;
    }
}
