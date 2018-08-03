package com.itheima.service.impl;

import com.itheima.dao.IProductDao;
import com.itheima.domain.Item;
import com.itheima.domain.Result;
import com.itheima.service.IPorductQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductQueryImpl implements IPorductQuery {
    @Autowired
    IProductDao iProductDao;
    @Override
    public Result listQuery(String queryString, String catalog_name, String price, String curPage, String sort) {
        QueryResponse response = iProductDao.listQuery(queryString, catalog_name, price, curPage, sort);
        SolrDocumentList docList = response.getResults();
        Result result = new Result();
        //设置页数
        result.setCurPage(curPage);
        long numFound = docList.getNumFound();
        //设置条数
        result.setCount(String.valueOf(numFound));
        //设置总页数
        long page = numFound%50==0?numFound/50:(numFound/50+1);
        result.setPage(String.valueOf(page));
        //封装ItemList
        ArrayList<Item> items = new ArrayList<>();

        //获得高亮数据
        Map<String, Map<String, List<String>>> HLMap = response.getHighlighting();

        for (SolrDocument document : docList) {
            Item item = new Item();
            //设置高亮数据
            List<String> HLNameList = HLMap.get(document.get("id")).get("product_name");
            if(HLNameList!=null&&HLNameList.size()!=0){
                item.setName(HLNameList.get(0));
            }else {
                item.setName((String) document.get("product_name"));
            }
            item.setId((String)document.get("id"));
            item.setPrice((float)document.get("product_price"));
            item.setPic((String)document.get("product_picture"));
            items.add(item);
        }
        result.setItemList(items);
        return result;
    }
}
