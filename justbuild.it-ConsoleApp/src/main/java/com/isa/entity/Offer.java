package com.isa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isa.entity.enums.ServiceCategory;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer implements Serializable {
    private long offerId;
    private ServiceCategory serviceCategory;
    private String offerContent;
    private String city;
    private User user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    protected Date date;

    public Offer() {
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Offer offer = (Offer) o;
        return offerId == offer.offerId && serviceCategory == offer.serviceCategory && Objects.equals(offerContent, offer.offerContent) && Objects.equals(city, offer.city) && Objects.equals(user, offer.user) && Objects.equals(date, offer.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), offerId, serviceCategory, offerContent, city, user, date);
    }

    @Override
    public String toString() {
        return String.format("Offer{offerID= \t%s\t, \tserviceCategory= \t%-20s, \tcity= \t%-15s, user= \t%-160s,\tdate= %s, \tofferContent= \t%s}", offerId, serviceCategory, city, user, date, offerContent);
    }

    public String printOffer() {
        return String.format("{\"offerID\" : %d, %n   \"serviceCategory\" : \"%s\", %n   \"offerContent\" : \"%s\", %n   \"city\" : \"%s\", %n   \"user\" : %s, %n   \"date\" : \"%s\"}",
                offerId, serviceCategory, offerContent, city, user.printUser(), date);
    }

}
