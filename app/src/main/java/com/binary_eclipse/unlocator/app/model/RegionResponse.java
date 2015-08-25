
package com.binary_eclipse.unlocator.app.model;

import com.google.gson.annotations.Expose;

public class RegionResponse {

    @Expose
    private Channels channels;

    /**
     * @return The channels
     */
    public Channels getChannels() {
        return channels;
    }

    /**
     * @param channels The channels
     */
    public void setChannels(Channels channels) {
        this.channels = channels;
    }

}
