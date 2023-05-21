package justbuild.it.web.app.entity;

import justbuild.it.web.app.entity.enums.ServiceCategoryEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long offerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_category")
    private ServiceCategoryEnum serviceCategory;

    @Column(name = "offer_content")
    private String offerContent;

    @Column(name = "city")
    private String city;

    @ManyToOne
    private User user;

    @Column(name = "date")
    private LocalDateTime date;

    private LocalDateTime expiryDate;

    public Offer() {
        this.offerId = 0L;
        this.serviceCategory = null;
        this.offerContent = "";
        this.city = "";
        this.user = new User();
        this.date = LocalDateTime.now();
        this.expiryDate = this.date.plusDays(30);
    }

    public Offer(Long offerID, ServiceCategoryEnum serviceCategory, String offerContent, String city, User user, LocalDateTime date) {
        this.offerId = offerID;
        this.serviceCategory = serviceCategory;
        this.offerContent = offerContent;
        this.city = city;
        this.user = user;
        this.date = date;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public ServiceCategoryEnum getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategoryEnum serviceCategory) {
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

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(offerId, offer.offerId) && serviceCategory == offer.serviceCategory && Objects.equals(offerContent, offer.offerContent) && Objects.equals(city, offer.city) && Objects.equals(user, offer.user) && Objects.equals(date, offer.date) && Objects.equals(expiryDate, offer.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerId, serviceCategory, offerContent, city, user, date, expiryDate);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId=" + offerId +
                ", serviceCategory=" + serviceCategory +
                ", offerContent='" + offerContent + '\'' +
                ", city='" + city + '\'' +
                ", user=" + user +
                ", date=" + date +
                ", expiryDate=" + expiryDate +
                '}';
    }
}

