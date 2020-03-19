package com.example.hik;

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
        private String key;
        private String secret;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
