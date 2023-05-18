package justbuild.it.web.app.entity.entities;

import justbuild.it.web.app.entity.enums.ServiceCategoryEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "offer")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long offerId;

    @Column(name = "service_category")
    private String serviceCategory;

    @Column(name = "offer_content")
    private String offerContent;

    @Column(name = "city")
    private String city;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "date")
    private LocalDateTime date;

    public OfferEntity(Long offerId, String serviceCategory, String offerContent, String city, UserEntity userEntity, LocalDateTime date) {
        this.offerId = offerId;
        this.serviceCategory = serviceCategory;
        this.offerContent = offerContent;
        this.city = city;
        this.userEntity = userEntity;
        this.date = date;
    }

    public OfferEntity() {
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public ServiceCategoryEnum setServiceCategory(ServiceCategoryEnum serviceCategory) {
        return serviceCategory;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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
        OfferEntity that = (OfferEntity) o;
        return Objects.equals(offerId, that.offerId) && Objects.equals(serviceCategory, that.serviceCategory) && Objects.equals(offerContent, that.offerContent) && Objects.equals(city, that.city) && Objects.equals(userEntity, that.userEntity) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerId, serviceCategory, offerContent, city, userEntity, date);
    }
}
