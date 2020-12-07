package com.tw.certmaster.results;

public class RequestResultExcel {
    private String userName;
    private String certificationTitle;
    private int quarter;
    private String categoryName;
    private String status;
    private int price;
    private String businessJustification;

    public RequestResultExcel(String userName, String certificationTitle, int quarter, String categoryName, String status, int price, String businessJustification) {
        this.userName = userName;
        this.certificationTitle = certificationTitle;
        this.quarter = quarter;
        this.categoryName = categoryName;
        this.status = status;
        this.price = price;
        this.businessJustification = businessJustification;
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
