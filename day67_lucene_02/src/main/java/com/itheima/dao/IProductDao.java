package com.itheima.dao;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public interface IProductDao {
    public QueryResponse listQuery(String queryString, String catalog_name, String price, String page, String sort);
}
