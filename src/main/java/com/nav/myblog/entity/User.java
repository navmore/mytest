package com.nav.myblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Primary;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "用户密码不能为空")
    @JsonIgnore
    private String password;
    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "用户昵称不能为空")
    private String nickname;

    private String isActive;

    private String headPhone;

    private String isCanUse;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }

    public String getHeadPhone() {
        return headPhone;
    }

    public void setHeadPhone(String headPhone) {
        this.headPhone = headPhone == null ? null : headPhone.trim();
    }

    public String getIsCanUse() {
        return isCanUse;
    }

    public void setIsCanUse(String isCanUse) {
        this.isCanUse = isCanUse == null ? null : isCanUse.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isActive='" + isActive + '\'' +
                ", headPhone='" + headPhone + '\'' +
                ", isCanUse='" + isCanUse + '\'' +
                '}';
    }
}