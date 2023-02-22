package justbuild.it.web.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import justbuild.it.web.app.model.Offer;
import justbuild.it.web.app.model.ServiceCategory;
import justbuild.it.web.app.model.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class OfferDTO {
    private Long offerID;
    private ServiceCategory serviceCategory;
    private String offerContent;
    @NotBlank
    private String city;
    private String userFirstName;
    private String userLastName;
    @NotBlank
    private String userCompanyName;
    @NotBlank
    private String userEmailAddress;
    @NotBlank
    private String userTelephoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateTime;

    public OfferDTO() {
        this.offerID = 0L;
        this.serviceCategory = null;
        this.offerContent = "";
        this.city = "";
        this.userFirstName = "";
        this.userLastName = "";
        this.userCompanyName = "";
        this.userEmailAddress = "";
        this.userTelephoneNumber = "";
        this.dateTime = LocalDateTime.now();
    }

    public OfferDTO(Long offerID, ServiceCategory serviceCategory, String offerContent, String city,
                    String userFirstName, String userLastName, String userCompanyName, String userEmailAddress,
                    String userTelephoneNumber, LocalDateTime dateTime) {
        this.offerID = offerID;
        this.serviceCategory = serviceCategory;
        this.offerContent = offerContent;
        this.city = city;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userCompanyName = userCompanyName;
        this.userEmailAddress = userEmailAddress;
        this.userTelephoneNumber = userTelephoneNumber;
        this.dateTime = dateTime;
    }

    public Offer toOffer() {
        Offer offer = new Offer();
        offer.setOfferID(offerID);
        offer.setServiceCategory(serviceCategory);
        offer.setOfferContent(offerContent);
        offer.setCity(city);
        offer.setDate(dateTime);
        User user = new User();
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);
        user.setCompany(userCompanyName);
        user.setEmailAddress(userEmailAddress);
        user.setTelephoneNumber(userTelephoneNumber);
        offer.setUser(user);
        return offer;
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public String getUserTelephoneNumber() {
        return userTelephoneNumber;
    }

    public void setUserTelephoneNumber(String userTelephoneNumber) {
        this.userTelephoneNumber = userTelephoneNumber;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
