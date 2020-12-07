package com.tw.certmaster.results;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestResult {

    private long requestId;
    private long userId;
    private String userName;
    private String certificationTitle;
    private long categoryId;
    private int quarter;
    private String categoryName;
    private String status;
    private int price;
    private String businessJustification;

    public RequestResult(long requestId, long userId, String userName, String certificationTitle, long categoryId, int quarter, String categoryName, String status, int price, String businessJustification) {
        this.requestId = requestId;
        this.userId = userId;
        this.userName = userName;
        this.certificationTitle = certificationTitle;
        this.categoryId = categoryId;
        this.quarter = quarter;
        this.categoryName = categoryName;
        this.status = status;
        this.price = price;
        this.businessJustification = businessJustification;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCertificationTitle() {
        return certificationTitle;
    }

    public void setCertificationTitle(String certificationTitle) {
        this.certificationTitle = certificationTitle;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBusinessJustification() {
        return businessJustification;
    }

    public void setBusinessJustification(String businessJustification) {
        this.businessJustification = businessJustification;
    }
}
