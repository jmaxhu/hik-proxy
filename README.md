# 海康视频接口调用代理

该项目为调用海康视频接口的代理，方便前端调用视频相关接口。

海康的视频接入文档地址为：(HikVision)[https://open.hikvision.com/docs/37e38899e583cfe4f9879a07a5294bf4]

## 使用说明

该项目只有一个接口地址为： **/api/hik**
只接收：**POST** 请求

请求的参数为：

 - ip, 视频监控平台的ip地址
 - apiUrl, 实际要调用的海康视频的api地址。
 - apiParams, 海康视频api接收的参数，以JSON格式序列化为字符串。
 
 返回结果为调用视频api地址的原始结果。
 
 ## 示例
 
 发送请求(请用真实ip代替):
 
 ```shell script
curl --location --request POST 'http://localhost:8090/api/hik' \
--header 'Content-Type: application/json' \
--data-raw '{
	"ip": "111.222.111.111",
	"apiUrl": "/api/resource/v1/camera/advance/cameraList",
	"apiParams": "{\"pageNo\":1,\"pageSize\":5}"
}'
```

返回结果：
```
HTTP/1.1 200 
Location: /api/hik
Content-Type: application/json
Content-Length: 4957
Date: Thu, 19 Mar 2020 14:26:50 GMT
Connection: close

{"code":"0","msg":"SUCCESS","data":{"total":6,"pageNo":1,"pageSize":5,"list":[{"altitude":null,"cameraIndexCode":"69c41a798a094b11a022b6ae5b22ddaf","cameraName":"第三通道","cameraType":0,"cameraTypeName":"枪"...
```

## 系统运行

```shell script
./mvnw spring-boot:run
```

## jib 创建 docker

```shell script
./mvnw compile jib:build -Dimage=jmaxhu/hik-proxy:v0.1.0
```