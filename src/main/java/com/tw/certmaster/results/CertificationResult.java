package com.tw.certmaster.results;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CertificationResult {

    private long id;
    private String certificationTitle;
    private int quarter;
    private long categoryId;
    private String categoryName;
    private int price;

    public CertificationResult(long id, String certificationTitle, int quarter, long categoryId, String categoryName, int price) {
        this.id = id;
        this.certificationTitle = certificationTitle;
        this.quarter = quarter;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categotyName) {
        this.categoryName = categotyName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
