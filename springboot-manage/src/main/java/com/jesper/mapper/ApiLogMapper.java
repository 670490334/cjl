package com.jesper.mapper;

import com.jesper.hftc.entity.ApiLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 廖凡
 * @Date 2020/2/19 15:41
 */
@Mapper
public interface ApiLogMapper {

    int save(ApiLog apiLog);
}
