package com.nav.myblog.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "upvote")
public class Upvote implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    private Integer zanId;

    private Integer userId;

    private Integer contentId;

    private String zanUserIp;

    private String dianzan;

    private String cai;

    private Date zanTime;

    public Integer getZanId() {
        return zanId;
    }

    public void setZanId(Integer zanId) {
        this.zanId = zanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getZanUserIp() {
        return zanUserIp;
    }

    public void setZanUserIp(String zanUserIp) {
        this.zanUserIp = zanUserIp == null ? null : zanUserIp.trim();
    }

    public String getDianzan() {
        return dianzan;
    }

    public void setDianzan(String dianzan) {
        this.dianzan = dianzan == null ? null : dianzan.trim();
    }

    public String getCai() {
        return cai;
    }

    public void setCai(String cai) {
        this.cai = cai == null ? null : cai.trim();
    }

    public Date getZanTime() {
        return zanTime;
    }

    public void setZanTime(Date zanTime) {
        this.zanTime = zanTime;
    }

    @Override
    public String toString() {
        return "Upvote{" +
                "zanId=" + zanId +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", zanUserIp='" + zanUserIp + '\'' +
                ", dianzan='" + dianzan + '\'' +
                ", cai='" + cai + '\'' +
                ", zanTime=" + zanTime +
                '}';
    }
}