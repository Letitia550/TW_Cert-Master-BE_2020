package com.ibm.certmaster.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@NoArgsConstructor
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "The status of the request cannot be empty.")
    private String status;

    @NotEmpty(message = "The business justification cannot be empty.")
    private String business_justification;

    @Positive(message = "The request must belong to an user.")
    private long user_id;

    @Positive(message = "The request must belong to a certification type.")
    private long certification_id;

    enum statusOptions {
        Approved,
        Pending,
        Rejected
    }

    public Request(String status, String business_justification, long user_id, long certification_id) {
        this.status = status;
        this.business_justification = business_justification;
        this.user_id = user_id;
        this.certification_id = certification_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusiness_justification() {
        return business_justification;
    }

    public void setBusiness_justification(String business_justification) {
        this.business_justification = business_justification;
    }

    public long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getCertification_id() {
        return certification_id;
    }
}
