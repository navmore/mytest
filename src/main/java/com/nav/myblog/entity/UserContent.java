package com.nav.myblog.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_content")
public class UserContent implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    private Integer contentId;

    private Integer userId;

    private String contentTitle;

    private String contentType;

    private String isPrivate;

    private Date upTime;

    private String headPhotoUrl;

    private String userNickname;

    private Integer zan;

    private Integer cai;

    private Integer pinglun;

    private String contentText;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle == null ? null : contentTitle.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate == null ? null : isPrivate.trim();
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public String getHeadPhotoUrl() {
        return headPhotoUrl;
    }

    public void setHeadPhotoUrl(String headPhotoUrl) {
        this.headPhotoUrl = headPhotoUrl == null ? null : headPhotoUrl.trim();
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname == null ? null : userNickname.trim();
    }

    public Integer getZan() {
        return zan;
    }

    public void setZan(Integer zan) {
        this.zan = zan;
    }

    public Integer getCai() {
        return cai;
    }

    public void setCai(Integer cai) {
        this.cai = cai;
    }

    public Integer getPinglun() {
        return pinglun;
    }

    public void setPinglun(Integer pinglun) {
        this.pinglun = pinglun;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText == null ? null : contentText.trim();
    }

    @Override
    public String toString() {
        return "UserContent{" +
                "contentId=" + contentId +
                ", userId=" + userId +
                ", contentTitle='" + contentTitle + '\'' +
                ", contentType='" + contentType + '\'' +
                ", isPrivate='" + isPrivate + '\'' +
                ", upTime=" + upTime +
                ", headPhotoUrl='" + headPhotoUrl + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", zan=" + zan +
                ", cai=" + cai +
                ", pinglun=" + pinglun +
                ", contentText='" + contentText + '\'' +
                '}';
    }
}