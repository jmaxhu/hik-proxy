package com.example.hik;

import com.alibaba.fastjson.JSON;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HikController {
    private final HikProperties hikProperties;

    public HikController(HikProperties hikProperties) {
        this.hikProperties = hikProperties;
    }

    @PostMapping("/hik")
    public ResponseEntity<String> request(@RequestBody HikRequest hikRequest) throws URISyntaxException {
        if (StringUtils.isEmpty(hikRequest.getIp()) || StringUtils.isEmpty(hikRequest.getApiUrl())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ip和api地址不能为空。");
        }

        List<HikProperties.VideoKey> videoInfos = hikProperties.getKeys();
        System.out.println(videoInfos);

        HikProperties.VideoKey info = hikProperties.getKeys().stream()
                .filter(x -> x.getIp().equals(hikRequest.getIp()))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "无效的ip"));

        ArtemisConfig.host = hikRequest.getIp();
        ArtemisConfig.appKey = info.getKey();
        ArtemisConfig.appSecret = info.getSecret();

        Map<String, String> path = new HashMap<>();
        path.put("https://", MessageFormat.format("/artemis{0}", hikRequest.getApiUrl()));

        String result = ArtemisHttpUtil.doPostStringArtemis(path, hikRequest.getApiParams(), null, null, "application/json");

        return ResponseEntity.ok()
                .header("content-type", "application/json")
                .body(result);
    }
}
