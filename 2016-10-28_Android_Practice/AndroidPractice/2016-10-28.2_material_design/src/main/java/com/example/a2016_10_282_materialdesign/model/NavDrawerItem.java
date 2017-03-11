package com.example.a2016_10_282_materialdesign.model;

/**
 * Created by MyndDev on 10/28/2016.
 */

public class NavDrawerItem {
    private boolean showNotify;
    private String title;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
