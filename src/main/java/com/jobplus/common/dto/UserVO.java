package com.jobplus.common.dto;

import com.jobplus.entity.User;

/**
 * 用户视图对象（排除password字段）
 */
public class UserVO {

    private Integer id;
    private String email;
    private String phone;
    private Integer role;
    private String nickname;
    private String avatarUrl;
    private Integer status;
    private String companyName;

    public UserVO() {}

    /**
     * 从User实体构造（不含密码）
     */
    public UserVO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.nickname = user.getNickname();
        this.avatarUrl = user.getAvatarUrl();
        this.status = user.getStatus();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
