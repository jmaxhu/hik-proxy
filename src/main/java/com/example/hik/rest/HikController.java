package com.example.hik.rest;

import com.example.hik.config.HikProperties;
import com.example.hik.rest.vm.HikRequest;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口代码
 *
 * @author maxwell
 * @since 2020-07-01
 */
@RestController
@RequestMapping("/api")
public class HikController {
    private final static Logger logger = LoggerFactory.getLogger(HikController.class);

    // 人员工种接口前缀
    private static final String CCMS = "/ccms/";

    private final HikProperties hikProperties;

    public HikController(HikProperties hikProperties) {
        this.hikProperties = hikProperties;
    }

    @PostMapping("/hik")
    public ResponseEntity<String> request(@RequestBody HikRequest hikRequest) throws URISyntaxException, IOException, NoSuchAlgorithmException, KeyManagementException {
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

        String apiUrl = hikRequest.getApiUrl();
        // 取人员相关数据，与视频平台接口分开
        if (apiUrl.contains(CCMS)) {
            return getCCMSRequest(hikRequest, apiUrl);
        }

        Map<String, String> path = new HashMap<>(1);
        String key = "https://";
        path.put(key, MessageFormat.format("/artemis{0}", apiUrl));

        String result = ArtemisHttpUtil.doPostStringArtemis(path, hikRequest.getApiParams(), null, null, "application/json");
        logger.debug("请求地址: {}，返回: {}", path.get(key), result);

        return ResponseEntity.ok()
                .header("content-type", "application/json")
                .body(result);
    }

    private ResponseEntity<String> getCCMSRequest(@RequestBody HikRequest hikRequest, String apiUrl) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        logger.debug("收到 ccms 请求: {}", apiUrl);
        /* Start of Fix */
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        URL url = new URL(MessageFormat.format("https://{0}{1}", hikRequest.getIp(), apiUrl));
        URLConnection conn = url.openConnection();
        InputStream inputstream = conn.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        StringBuilder sb = new StringBuilder(1024);
        String string = null;
        while ((string = bufferedreader.readLine()) != null) {
            sb.append(string);
        }

        String resBody = sb.toString();
        if (StringUtils.isEmpty(resBody)) {
            logger.warn("请求的返回结果为空。");
            return ResponseEntity.ok("");
        }
        logger.debug(resBody);
        return ResponseEntity.ok()
                .header("content-type", "application/json")
                .body(resBody);
    }


}
