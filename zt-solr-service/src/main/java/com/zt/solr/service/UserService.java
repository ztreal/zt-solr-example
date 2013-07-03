package com.zt.solr.service;

import com.zt.solr.dto.UserIndexDto;

/**
 * User: zhangtan
 * Date: 12-9-7
 * Time: 上午9:47
 */
public interface UserService {
    /**
     * 根据地址获取坐标
     * @param address
     * @return
     */
    public String getGeo(String address) ;

    /**
     * 根据经纬度和距离查询店铺
     * @param lng
     * @param lat
     * @param distance
     * @return
     */
    public UserIndexDto serachByGeo(String lng , String lat, String distance);
}
