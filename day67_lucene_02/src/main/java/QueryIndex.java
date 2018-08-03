import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class QueryIndex {
    @Test
    public void queryIndex() throws Exception {
        //连接Solr服务
        String basuURL = "http://localhost:8080/solr/zeldacollection";
        HttpSolrServer httpSolrServer = new HttpSolrServer(basuURL);

        //创建SolrInputDocument对象
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","2");
        doc.addField("jid","1");
        doc.addField("jtitle","摇摆");
        doc.addField("jcontent","热爱疯狂,追寻生命中的美丽,精通桌球篮球足球羽毛球划水皮皮球排球");
        doc.addField("jhobby","游戏无敌,精通CS魔兽争霸英雄联盟神武");
        doc.addField("jskilled","跳舞");

        //提交
        httpSolrServer.add(doc);
        httpSolrServer.commit();
    }


    @Test
    public void DeleteById() throws Exception {
        String url = "http://localhost:8080/solr/zeldacollection";
        HttpSolrServer  httpSolrServer = new HttpSolrServer(url);

        httpSolrServer.deleteById("2");
        httpSolrServer.commit();
    }

    @Test
    public void deleteByQuery() throws Exception {
        String url = "http://localhost:8080/solr/zeldacollection";
        HttpSolrServer  httpSolrServer = new HttpSolrServer(url);

        httpSolrServer.deleteByQuery("jtitle:摇摆");
        httpSolrServer.commit();
    }
}
