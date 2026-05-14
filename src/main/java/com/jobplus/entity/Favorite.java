package com.jobplus.entity;

import java.util.Date;

/**
 * 收藏
 */
public class Favorite {

    private Integer id;
    private Integer userId;
    private Integer positionId;
    private Date createdAt;

    public Favorite() {}

    public Favorite(Integer id, Integer userId, Integer positionId, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.positionId = positionId;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", userId=" + userId +
                ", positionId=" + positionId +
                '}';
    }
}
