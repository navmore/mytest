package com.nav.myblog.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "resource")
public class Resource implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @Field(value = "id") //对应的是文档里的id，文档必须要有id属性
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;
    @Field
    private String resourceName;
    @Field
    private String resourceUrl;
    @Field
    private String resourceCanuse;
    @Field
    private String resourceComment;

    @Field
    private Integer lastmodify;
    private Integer isdelete;

    public Resource() {
    }
}