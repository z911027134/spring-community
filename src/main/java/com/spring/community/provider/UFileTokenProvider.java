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
        String data = "";
        data += uFileTokenDTO.getMethod().toUpperCase() + "\n";
        data += uFileTokenDTO.getContent_md5() + "\n";
        data += uFileTokenDTO.getContent_type() + "\n";
        if (uFileTokenDTO.getData() != null && uFileTokenDTO.getData().length() > 0){
            data += uFileTokenDTO.getData() + "\n";
        }else{
            data += "\n";
        }
        data += this.canonicalizedResource(uFileTokenDTO.getBucket(), uFileTokenDTO.getKey());
        String sign = "";
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
        if (!uFileTokenDTO.getPut_policy().isEmpty()) {
            String putPolicy = JSON.toJSONString(uFileTokenDTO.getPut_policy());
            putPolicy = putPolicy.replace("\"", "\\\"");
            putPolicy = Base64.getEncoder().encodeToString(putPolicy.getBytes());
            sign = sign + ":" + putPolicy;
        }
        return sign;
    }
}
