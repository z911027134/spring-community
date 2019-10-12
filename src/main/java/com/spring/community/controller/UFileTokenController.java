package com.spring.community.controller;

import com.spring.community.dto.UFileTokenDTO;
import com.spring.community.provider.UFileTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UFileTokenController {

    @Value("${ufile.public.key}")
    private String ufilePublicKey;

    @Value("${ufile.private.key}")
    private String ufilePrivateKey;

    @Autowired
    private UFileTokenProvider uFileTokenProvider;

    @ResponseBody
    @RequestMapping("/ufileToken")
    public String getToken(UFileTokenDTO uFileTokenDTO) {
        uFileTokenDTO.setUcloudPublicKey(ufilePublicKey);
        uFileTokenDTO.setUcloudPrivateKey(ufilePrivateKey);
        return uFileTokenProvider.signRequest(uFileTokenDTO);
    }
}
