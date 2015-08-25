package com.binary_eclipse.unlocator.app.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andras on 15/07/06.
 */
public class Channel {

    @Expose
    private List<String> country = new ArrayList<String>();

    /**
     *
     * @return
     *     The country
     */
    public List<String> getCountry() {
        return country;
    }

    /**
     *
     * @param country
     *     The country
     */
    public void setCountry(List<String> country) {
        this.country = country;
    }
}
