package com.zt.solr.util.solr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhangtan
 * Date: 13-7-2
 * Time: 下午4:33
 */
@Service
public class SolrClient implements InitializingBean {
//    @Resource
    String solrServerUrl = "http://42.121.52.175:8983/solr/";

    protected transient Log log = LogFactory.getLog(getClass());

    private static SolrServer  server;

    @Override
    public void afterPropertiesSet() throws Exception {
         server = new HttpSolrServer(solrServerUrl);
    }

    public QueryResponse queryBySolrQuery(SolrQuery query){
        QueryResponse rsp =null;
        try {
             rsp = server.query(query);
        } catch (SolrServerException e) {
            log.error("Method queryBySolrQuery error -->",e);
        }
        return  rsp;
    }
}
