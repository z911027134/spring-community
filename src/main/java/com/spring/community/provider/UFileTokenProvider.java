package com.spring.community.provider;

import com.alibaba.fastjson.JSON;
import com.spring.community.dto.UFileTokenDTO;
import com.spring.community.exception.CustomizeErrorCode;
import com.spring.community.exception.CustomizeException;
import com.spring.community.tool.HMAC;
import com.spring.community.tool.HMACEnum;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UFileTokenProvider {

    public String signRequest(UFileTokenDTO uFileTokenDTO) {
        StringBuilder builder = new StringBuilder();
        builder.append(uFileTokenDTO.getMethod().toUpperCase());
        builder.append("\n");
        builder.append(uFileTokenDTO.getContentMd5());
        builder.append("\n");
        builder.append(uFileTokenDTO.getContentType());
        builder.append("\n");
        builder.append(uFileTokenDTO.getData());
        builder.append("\n");
        String data = builder.toString();
        data += this.canonicalizedResource(uFileTokenDTO.getBucket(), uFileTokenDTO.getKey());

        String sign = null;
        try {
            sign = this.sign(data, uFileTokenDTO);
        } catch (Exception e) {
            throw new CustomizeException(CustomizeErrorCode.SYS_ERROR);
        }
        return sign;
    }

    private String canonicalizedResource(String bucket, String key) {
        return "/" + bucket + "/" + key;
    }

    private String sign(String data, UFileTokenDTO uFileTokenDTO) throws Exception {
        HMAC hmac = new HMAC(HMACEnum.HMACSHA1);
        byte[] bytes = hmac.getSignatureRaw(data, uFileTokenDTO.getUcloudPrivateKey());
        String sign = Base64.getEncoder().encodeToString(bytes);
        sign = "UCloud " + uFileTokenDTO.getUcloudPublicKey() + ":" + sign;
        if (!uFileTokenDTO.getPutPolicy().isEmpty()) {
            String putPolicy = JSON.toJSONString(uFileTokenDTO.getPutPolicy());
            putPolicy = putPolicy.replace("\"", "\\\"");
            putPolicy = Base64.getEncoder().encodeToString(putPolicy.getBytes());
            sign = sign + ":" + putPolicy;
        }

        return sign;
    }
}
