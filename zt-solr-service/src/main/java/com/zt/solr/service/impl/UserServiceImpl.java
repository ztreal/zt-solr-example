package com.zt.solr.service.impl;


import com.zt.solr.domain.user.User;
import com.zt.solr.dto.UserIndexDto;
import com.zt.solr.service.BaseService;
import com.zt.solr.service.UserService;
import com.zt.solr.util.solr.SolrClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zhangtan
 * Date: 12-9-7
 * Time: 上午9:47
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
    @Resource
    private SolrClient solrClient;

    public String getGeo(String address) {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new
                Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        ClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        HttpClient httpClient = new DefaultHttpClient(cm);
        try {
            address = URLEncoder.encode(address, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("urlencode error,target is utf-8, adress is "+address,e);
        }
        HttpGet httpget = new HttpGet("http://api.map.baidu.com/geocoder?address=" + address + "&output=json&key=78ff589600ab028715dad8a2ee4ce618");
        log.info("http://api.map.baidu.com/geocoder?address=" + address + "&output=json&key=78ff589600ab028715dad8a2ee4ce618");
        HttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpget);
        } catch (IOException e) {
            log.error("connect solr server is IoException ",e);
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                result = EntityUtils.toString(entity);
                log.info("EntityUtils.toString(entity)" + result);
            } catch (IOException e) {
                log.error("EntityUtils.toString is IoException ",e);
            }
            // do something useful with the entity
        }
        return result;
    }

    /**
     * 根据坐标查询
     * @param lng
     * @param lat
     * @param distance
     * @return
     */
    public UserIndexDto serachByGeo(String lng, String lat, String distance) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.addFilterQuery("{!geofilt pt=" + lng + "," + lat + " sfield=store d=" + distance + "}");
        query.addSort("geodist(store,"+lng+","+lat+")", SolrQuery.ORDER.asc);//距离排序
        log.info("query is "+query.toString());
        SolrDocumentList docList = solrClient.queryBySolrQuery(query).getResults();
        return changeRsToDtoList(docList);
    }

    /**
     * 转换solrDoc对象到Dto
     * @param docList
     * @return
     */
    private UserIndexDto changeRsToDtoList(SolrDocumentList docList) {
        UserIndexDto userIndexDto = new UserIndexDto();
        List<User> userList = new ArrayList<User>();
        for (SolrDocument doc : docList) {
            User user = new User();
            if (doc.getFieldValue("id") != null) user.setUserId(doc.getFieldValue("id").toString());
            if (doc.getFieldValue("store") != null) {
                String store = doc.getFieldValue("store").toString();
                String lng = store.substring(0, store.indexOf(","));
                user.setLng(lng);
                String lat = store.substring(store.indexOf(",")+1);
                user.setLat(lat);
            }
            userList.add(user);
        }
        userIndexDto.setUserList(userList);
        return userIndexDto;
    }


}
