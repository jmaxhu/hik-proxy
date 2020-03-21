package com.example.hik.rest.vm;

public class HikRequest {
    private String ip;
    private String appKey;
    private String appSecret;
    private String apiUrl;
    private String apiParams;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiParams() {
        return apiParams;
    }

    public void setApiParams(String apiParams) {
        this.apiParams = apiParams;
    }
}
