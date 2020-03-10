package com.jesper.hftc.config;

/**
 * @Author 廖凡
 * @Date 2020/2/19 15:40
 */
public interface MyConfig {
    final String SUCCESS = "true";

    final String FAILD = "false";
    final Integer PAY_PAY =1;//完成支付
    final Integer PAY_NOT = 2;//未支付
    final Integer PAY_FINISHED = 3;
}
