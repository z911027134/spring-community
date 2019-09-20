package com.spring.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "问题不存在，请重试"),
    COMMENT_NOT_FOUND(2002, "评论不存在，请重试"),
    USER_NOT_LOGIN(2003, "用户未登陆，请先登陆"),
    SYS_ERROR(2004, "系统错误，请稍后再试"),
    TARGET_PARAM_NOT_FOUND(2005, "未选中问题或者评论进行回复"),
    COMMENT_TYPE_NOT_FOUND(2006, "评论类型不存在，请确认"),
    PARAMS_ERROR(2007, "请求错误，请检查参数"),
    COMMENT_CONTENT_EMPTY(2008, "评论内容不能为空"),
    ;

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
