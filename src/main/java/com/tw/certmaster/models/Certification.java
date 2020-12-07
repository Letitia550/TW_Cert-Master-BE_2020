package com.tw.certmaster.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "certifications")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String title;

    private int quarter;

    private long category_id;

    private int price;

    public Certification(String title, int quarter, int price, long category_id) {
        this.title=title;
        this.quarter=quarter;
        this.price=price;
        this.category_id=category_id;
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "certification_id")
    @JsonIgnore
    private List<Request> requests = new ArrayList<>();

    public List<Request> getRequests() {
        return requests;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public long getId() {
        return id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
