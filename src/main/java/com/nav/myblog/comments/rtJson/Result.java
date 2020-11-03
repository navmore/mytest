package com.nav.myblog.comments.rtJson;



import lombok.Data;

import java.io.Serializable;
/**
 * 前后端交互标准
 * */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 成功标志
     * */
    private boolean success;
    /**
     * 返回信息
     * */
    private String message;
    /**
     * 返回码
     * */
    private Integer code;
    /**
     * 返回的数据
     * */
    private T result;
}
