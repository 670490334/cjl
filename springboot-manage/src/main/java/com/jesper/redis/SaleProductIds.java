package com.jesper.redis;

/**
 * @Author 廖凡
 * @Date 2020/3/4 15:24
 */
public class SaleProductIds extends BasePrefix {
    public static final int BOARD_EXPIRE = 600;//默认5分钟

    /**
     * 防止被外面实例化
     */
    private SaleProductIds(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static SaleProductIds board = new SaleProductIds(BOARD_EXPIRE,"CGCKIDS");

    public static SaleProductIds keys = new SaleProductIds(BOARD_EXPIRE,"SPKEYS");

    public static SaleProductIds customerBuy = new SaleProductIds(BOARD_EXPIRE,"HFCUSTOMER");
}
