package com.nav.myblog.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Table(name = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    private Integer roleId;

    private String roleName;

    private String roleVal;

    private String roleUrl;

    private String roleCan;

    private String comment;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleVal() {
        return roleVal;
    }

    public void setRoleVal(String roleVal) {
        this.roleVal = roleVal == null ? null : roleVal.trim();
    }

    public String getRoleUrl() {
        return roleUrl;
    }

    public void setRoleUrl(String roleUrl) {
        this.roleUrl = roleUrl == null ? null : roleUrl.trim();
    }

    public String getRoleCan() {
        return roleCan;
    }

    public void setRoleCan(String roleCan) {
        this.roleCan = roleCan == null ? null : roleCan.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleVal='" + roleVal + '\'' +
                ", roleUrl='" + roleUrl + '\'' +
                ", roleCan='" + roleCan + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}