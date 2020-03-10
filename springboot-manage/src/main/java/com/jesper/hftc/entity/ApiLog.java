package com.jesper.hftc.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author 廖凡
 * @Date 2020/2/19 15:37
 */
@Data
public class ApiLog {
    private Integer id;

    private Date createTime;

    private String methodName;

    private String success;

    private String params;

    private String msg;
}
