package com.jobplus.common.dto;

import com.jobplus.entity.Position;

/**
 * 职位视图对象
 */
public class PositionVO extends Position {

    private String companyName;
    private String companyLogo;
    private String categoryName;
    private Boolean isFavorited;

    public PositionVO() {}

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    @Override
    public String toString() {
        return "PositionVO{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", companyName='" + companyName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", isFavorited=" + isFavorited +
                '}';
    }
}
