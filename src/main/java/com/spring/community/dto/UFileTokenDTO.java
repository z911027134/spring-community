package com.spring.community.dto;

import lombok.Data;

@Data
public class UFileTokenDTO {

    private String ucloudPublicKey;

    private String ucloudPrivateKey;

    // 存储空间名称
    private String bucket;

    // 文件md5值
    private String content_md5;

    // 文件类型
    private String content_type;

    // http 请求方式 大写字母
    private String method;

    // 文件名称
    private String key;

    private String data;

    /**
     * putPolicy 是一个传送给ufile的json,格式如下,如果需要可以在前端传输过来，也可以后端写死
     *     {
     *         "callbackUrl" : "http://test.ucloud.cn",   //指定回调服务的地址
     *         "callbackBody" : "key1=value1&key2=value2" //传递给回调服务的参数
     *     }
     */
    private String put_policy;
}
