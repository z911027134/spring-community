package com.spring.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    public Integer id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;

}
