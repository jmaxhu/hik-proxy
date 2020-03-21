package com.example.hik.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "hik")
public class HikProperties {

    private List<VideoKey> keys = new ArrayList<>();

    public List<VideoKey> getKeys() {
        return keys;
    }

    public static class VideoKey {
        private String ip;
        private String appKey;
        private String appSecret;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }
    }
}
