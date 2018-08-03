package com.itheima.dao.impl;

import com.itheima.dao.IProductDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl implements IProductDao{
    @Autowired
    HttpSolrServer httpSolrServer;

    @Override
    public QueryResponse listQuery(String queryString, String catalog_name, String price, String curPage, String sort) {
        SolrQuery solrQuery = new SolrQuery();
            //设置主查
            solrQuery.set("q","*:*");
        if(queryString!=null&&!queryString.equals("")){
            solrQuery.set("q","product_keywords:"+queryString);
        }
            //设置分类过滤
        if(catalog_name!=null&&(!catalog_name.equals(""))) {
            String filterString = "product_catalog_name:" + catalog_name;
            solrQuery.setFilterQueries(filterString);
        }
            //设置价格过滤
         if(price!=null&&(!price.equals(""))) {
             String[] num = price.split("-");
             solrQuery.setFilterQueries("product_price:[" + num[0] + " TO " + num[1] + "]");
         }
            //设置排序
        int i = Integer.valueOf(sort);
        if(i==1) {
                solrQuery.setSort("product_price", SolrQuery.ORDER.asc);
            }else {
                solrQuery.setSort("product_price", SolrQuery.ORDER.desc);
            }
            //设置查询条数
            solrQuery.setRows(50);
            solrQuery.setStart(Integer.parseInt(curPage)*50);
            //设置回显字段
            solrQuery.setFields("product_price product_name id product_picture");
            //设置查询高亮
            solrQuery.setHighlight(true);
            solrQuery.addHighlightField("product_name");
            solrQuery.setHighlightSimplePre("<font color='red'>");
            solrQuery.setHighlightSimplePost("</font>");

        QueryResponse response=null;
        try {
            //执行查询
            response = httpSolrServer.query(solrQuery);

        } catch (SolrServerException e) {
            e.printStackTrace();

        }
        return response;
    }

}
