package com.nav.myblog.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    private Integer commentId;

    private Integer contentId;

    private Integer userId1;

    private Integer userId2;

    private Date comentTime;

    private String comentIds;

    private Integer zanNum;

    private String comentText;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(Integer userId1) {
        this.userId1 = userId1;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    public Date getComentTime() {
        return comentTime;
    }

    public void setComentTime(Date comentTime) {
        this.comentTime = comentTime;
    }

    public String getComentIds() {
        return comentIds;
    }

    public void setComentIds(String comentIds) {
        this.comentIds = comentIds == null ? null : comentIds.trim();
    }

    public Integer getZanNum() {
        return zanNum;
    }

    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }

    public String getComentText() {
        return comentText;
    }

    public void setComentText(String comentText) {
        this.comentText = comentText == null ? null : comentText.trim();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", contentId=" + contentId +
                ", userId1=" + userId1 +
                ", userId2=" + userId2 +
                ", comentTime=" + comentTime +
                ", comentIds='" + comentIds + '\'' +
                ", zanNum=" + zanNum +
                ", comentText='" + comentText + '\'' +
                '}';
    }
}