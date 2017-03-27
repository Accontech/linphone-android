package com.peeredge.http.models;

import java.util.ArrayList;

/**
 * Created by root on 3/27/17.
 */

public class ProviderDbItem {

    private String _id;
    private String name;
    private String sipServer;
    private String proxy;
    private String logo;
    private ArrayList<Codec> codecs;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSipServer() {
        return sipServer;
    }

    public void setSipServer(String sipServer) {
        this.sipServer = sipServer;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ArrayList<Codec> getCodecs() {
        return codecs;
    }

    public void setCodecs(ArrayList<Codec> codecs) {
        this.codecs = codecs;
    }
}
