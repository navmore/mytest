package com.nav.myblog.controller;

import com.nav.myblog.comments.rtJson.Result;
import com.nav.myblog.comments.rtJson.ResultUtil;
import com.nav.myblog.entity.Resource;
import com.nav.myblog.service.ResourceService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/resource")
@RestController
public class ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private SolrClient solrClient;
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<Resource> getAll(@PathVariable String id) throws IOException, SolrServerException {
        Resource resource = new Resource();

        SolrDocument document =   solrClient.getById(id);
        if(document != null) {
            Map<String, Object> map = document.getFieldValueMap();
            resource.setResourceUrl((String) map.get("resourceUrl"));
            resource.setResourceComment((String) map.get("resourceComment"));
            resource.setResourceName((String) map.get("resourceName"));
            resource.setResourceCanuse((String) map.get("resourceCanuse"));
            resource.setResourceId(Integer.parseInt(id));
            resource.setLastmodify(Integer.parseInt(map.get("lastmodify").toString()));
        }else {
            resource = resourceService.queryById(Integer.parseInt(id));
        }
        return ResultUtil.data(resource);
    }
    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public Result delById(@PathVariable String id) throws IOException, SolrServerException {
        Resource resource = new Resource();
        resource.setResourceId(Integer.parseInt(id));
        resource.setLastmodify((int) System.currentTimeMillis());
        resource.setIsdelete(1);
        resourceService.updateSelective(resource);
        solrClient.deleteById(id);
        solrClient.commit();
        return ResultUtil.success("ok");
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result findByName() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        List<Resource> ls = new ArrayList<>();
        Resource resource;
        query.setQuery("resourceName:‘片'");
        query.setStart(0);
        query.setRows(2);
        QueryResponse res = solrClient.query(query);
        for(SolrDocument document: res.getResults()) {
            resource = new Resource();
            resource.setResourceUrl((String) document.get("resourceUrl"));
            resource.setResourceComment((String) document.get("resourceComment"));
            resource.setResourceName((String) document.get("resourceName"));
            resource.setResourceCanuse((String) document.get("resourceCanuse"));
            resource.setResourceId(Integer.parseInt(document.get("id").toString()));
            resource.setLastmodify(Integer.parseInt(document.get("lastmodify").toString()));
            ls.add(resource);
        }
        return ResultUtil.data(ls);
    }

    /**
     * 将SolrDocument转换成Bean
     * @param record
     * @param clazz
     * @return
     */
    public static Object toBean(SolrDocument record, Class clazz){
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            Object value = record.get(field.getName());
            try {
                BeanUtils.setProperty(obj, field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
    /**
     * 将SolrDocumentList转换成BeanList
     * @param records
     * @param clazz
     * @return
     */
    public static  Object toBeanList(SolrDocumentList records, Class clazz){
        List list = new ArrayList();
        for(SolrDocument record : records){
            list.add(toBean(record,clazz));
        }
        return list;
    }
}
