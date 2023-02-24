package justbuild.it.web.app.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Offer {

    private Long offerID;
    private ServiceCategory serviceCategory;
    private String offerContent;
    private String city;
    private User user;
    private LocalDateTime date;

    public Offer() {
        this.offerID = 0L;
        this.serviceCategory = null;
        this.offerContent = "";
        this.city = "";
        this.user = new User();
        this.date = LocalDateTime.now();
    }

    public Offer(Long offerID, ServiceCategory serviceCategory, String offerContent, String city, User user, LocalDateTime date) {
        this.offerID = offerID;
        this.serviceCategory = serviceCategory;
        this.offerContent = offerContent;
        this.city = city;
        this.user = user;
        this.date = date;
    }

    public Long getOfferID() {
        return offerID;
    }

    public void setOfferID(Long offerID) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(offerID, offer.offerID) && serviceCategory == offer.serviceCategory && Objects.equals(offerContent, offer.offerContent) && Objects.equals(city, offer.city) && Objects.equals(user, offer.user) && Objects.equals(date, offer.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerID, serviceCategory, offerContent, city, user, date);
    }
}
