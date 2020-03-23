package com.example.hik.rest;

import com.example.hik.config.HikProperties;
import com.example.hik.rest.vm.HikRequest;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HikController {
    private final HikProperties hikProperties;

    public HikController(HikProperties hikProperties) {
        this.hikProperties = hikProperties;
    }

    @PostMapping("/hik")
    public ResponseEntity<String> request(@RequestBody HikRequest hikRequest) {
        if (StringUtils.isEmpty(hikRequest.getIp()) || StringUtils.isEmpty(hikRequest.getApiUrl())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ip和api地址不能为空。");
        }

        String appKey = hikRequest.getAppKey();
        String appSecret = hikRequest.getAppSecret();
        if (StringUtils.isEmpty(appKey)) {
            HikProperties.VideoKey info = hikProperties.getKeys().stream()
                    .filter(x -> x.getIp().equals(hikRequest.getIp()))
                    .findFirst()
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "无效的ip"));
            appKey = info.getAppKey();
            appSecret = info.getAppSecret();
        }

        ArtemisConfig.host = hikRequest.getIp();
        ArtemisConfig.appKey = appKey;
        ArtemisConfig.appSecret = appSecret;

        Map<String, String> path = new HashMap<>();
        path.put("https://", MessageFormat.format("/artemis{0}", hikRequest.getApiUrl()));

        String result = ArtemisHttpUtil.doPostStringArtemis(path, hikRequest.getApiParams(), null, null, "application/json");

        return ResponseEntity.ok()
                .header("content-type", "application/json")
                .body(result);
    }
}
