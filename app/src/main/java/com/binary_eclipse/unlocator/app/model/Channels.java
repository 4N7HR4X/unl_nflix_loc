
package com.binary_eclipse.unlocator.app.model;

import com.google.gson.annotations.Expose;

public class Channels {

    public Channel getNetflix() {
        return netflix;
    }

    public void setNetflix(Channel netflix) {
        this.netflix = netflix;
    }

    public Channel getAmazon() {
        return amazon;
    }

    public void setAmazon(Channel amazon) {
        this.amazon = amazon;
    }

    public Channel getZattoo() {
        return zattoo;
    }

    public void setZattoo(Channel zattoo) {
        this.zattoo = zattoo;
    }

    public Channel getTv3() {
        return tv3;
    }

    public void setTv3(Channel tv3) {
        this.tv3 = tv3;
    }

    public Channel getViaplay() {
        return viaplay;
    }

    public void setViaplay(Channel viaplay) {
        this.viaplay = viaplay;
    }

    public Channel getMagine() {
        return magine;
    }

    public void setMagine(Channel magine) {
        this.magine = magine;
    }

    public Channel getRegionpack() {
        return regionpack;
    }

    public void setRegionpack(Channel regionpack) {
        this.regionpack = regionpack;
    }

    public Channel getSonytv() {
        return sonytv;
    }

    public void setSonytv(Channel sonytv) {
        this.sonytv = sonytv;
    }

    public Channel getRoku() {
        return roku;
    }

    public void setRoku(Channel roku) {
        this.roku = roku;
    }

    public Channel getNexus() {
        return nexus;
    }

    public void setNexus(Channel nexus) {
        this.nexus = nexus;
    }

    @Expose
    private Channel netflix;
    @Expose
    private Channel amazon;
    @Expose
    private Channel zattoo;
    @Expose
    private Channel tv3;
    @Expose
    private Channel viaplay;
    @Expose
    private Channel magine;
    @Expose
    private Channel regionpack;
    @Expose
    private Channel sonytv;
    @Expose
    private Channel roku;
    @Expose
    private Channel nexus;

}
