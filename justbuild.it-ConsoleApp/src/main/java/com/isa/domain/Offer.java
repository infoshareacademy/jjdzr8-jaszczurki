package com.isa.domain;

import com.isa.entity.enums.ServiceCategory;

import java.util.Date;

public class Offer {
    private long offerID;
    private ServiceCategory serviceCategory;
    private String offerContent;
    private String city;
    private User user;
    private Date date;

    public Offer() {
    }

    public long getOfferID() {
        return offerID;
    }

    public void setOfferID(long offerID) {
        this.offerID = offerID;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getOfferContent() {
        return offerContent;
    }

    public void setOfferContent(String offerContent) {
        this.offerContent = offerContent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerID=" + offerID +
                ", serviceCategory=" + serviceCategory +
                ", offerContent='" + offerContent + '\'' +
                ", city='" + city + '\'' +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
