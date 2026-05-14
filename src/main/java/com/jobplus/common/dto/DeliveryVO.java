package com.jobplus.common.dto;

import com.jobplus.entity.Delivery;

/**
 * 投递视图对象
 */
public class DeliveryVO extends Delivery {

    private String positionTitle;
    private String companyName;
    private String candidateName;

    public DeliveryVO() {}

    public DeliveryVO(Delivery delivery) {
        this.setId(delivery.getId());
        this.setUserId(delivery.getUserId());
        this.setPositionId(delivery.getPositionId());
        this.setResumeId(delivery.getResumeId());
        this.setStatus(delivery.getStatus());
        this.setCompanyRead(delivery.getCompanyRead());
        this.setReadAt(delivery.getReadAt());
        this.setCreatedAt(delivery.getCreatedAt());
        this.setUpdatedAt(delivery.getUpdatedAt());
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    @Override
    public String toString() {
        return "DeliveryVO{" +
                "id=" + getId() +
                ", positionTitle='" + positionTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", candidateName='" + candidateName + '\'' +
                '}';
    }
}
