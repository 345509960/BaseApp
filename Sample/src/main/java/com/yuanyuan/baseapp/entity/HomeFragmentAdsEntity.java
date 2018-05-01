package com.yuanyuan.baseapp.entity;

/**
 * Created by Aron on 2016/1/11.
 */
public class HomeFragmentAdsEntity {
    private String AdIcon;
    private String AdUrl;
    private String ad_name;

    public String getAdIcon() {
        return AdIcon;
    }

    public void setAdIcon(String adIcon) {
        AdIcon = adIcon;
    }

    public String getAdUrl() {
        return AdUrl;
    }

    public void setAdUrl(String adUrl) {
        AdUrl = adUrl;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    @Override
    public String toString() {
        return "HomeFragmentAdsEntity{" +
                "AdIcon='" + AdIcon + '\'' +
                ", AdUrl='" + AdUrl + '\'' +
                ", ad_name='" + ad_name + '\'' +
                '}';
    }
}
