package com.spring.community.tool;

public enum HMACEnum {
    HMACMD5("HmacMD5"),
    HMACSHA1("HmacSHA1"),
    HMACSHA256("HmacSHA256"),
    HMACSHA384("HmacSHA384"),
    HMACSHA512("HmacSHA512");

    public String mode;

    HMACEnum(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
