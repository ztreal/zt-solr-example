package com.zt.solr.util.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhangtan
 * Date: 13-6-28
 * Time: 下午2:34
 */
public class SolrTest {
    public static void main(String args[]) throws IOException, SolrServerException {
        String url = "http://42.121.52.175:8983/solr/";
        SolrServer server = new HttpSolrServer(url);

        //增加数据
//        SolrInputDocument doc1 = new SolrInputDocument();
//        doc1.addField("id", "id1", 1.0f);
//        doc1.addField("name", "doc1", 1.0f);
//        doc1.addField("price", 10);
//        SolrInputDocument doc2 = new SolrInputDocument();
//        doc2.addField("id", "id2", 1.0f);
//        doc2.addField("name", "doc2", 1.0f);
//        doc2.addField("price", 20);
//        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//        docs.add(doc1);
////        server.add( docs);
////        server.commit();
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
//        System.out.println(rsp.toString());

        //solruery查询
//        SolrQuery query = new SolrQuery();
//        query.setQuery("*:*");
//        query.setQuery("id=SP2514N");
//        query.addSortField("price", SolrQuery.ORDER.asc);
//        QueryResponse rsp = server.query(query);
//        SolrDocumentList docs = rsp.getResults();
//        System.out.println(docs.toString());

         //param查询
//        ModifiableSolrParams params = new ModifiableSolrParams();
//        params.set("q", "id:SP2514N");
//        params.set("defType", "edismax");
//        params.set("start", "0");
//        QueryResponse response = server.query(params);
//        System.out.println(response.toString());

        //经纬度查询
//        //q=*:*&sfield=store&pt=45.15,-93.85&sort=geodist()%20asc
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        //{!geofilt pt=35.0752,97.032 sfield=store d=500}
        query.addFilterQuery("{!geofilt pt=35.0752,-97.032 sfield=store d=5000}");
//        query.addSort("price", SolrQuery.ORDER.asc); //价钱排序
        query.addSort("geodist(store,35.0752,-97.032)", SolrQuery.ORDER.asc);//距离排序
        System.out.println(query.toString());
        QueryResponse rsp = server.query(query);
//        SolrDocumentList docs = rsp.getResults();
       //id=SP2514N, name=Samsung SpinPoint P120 SP2514N - hard drive - 250 GB - ATA-133, manu=Samsung Electronics Co. Ltd., manu_id_s=samsung, cat=[electronics, hard drive],
       // features=[7200RPM, 8MB cache, IDE Ultra ATA-133, NoiseGuard, SilentSeek technology, Fluid Dynamic Bearing (FDB) motor],
       // price=92.0, price_c=92,USD, popularity=6, inStock=true, manufacturedate_dt=Mon Feb 13 23:26:37 CST 2006, store=35.0752,-97.032, _version_=1439322324526432256}
        System.out.println(rsp.toString());


    }


}

