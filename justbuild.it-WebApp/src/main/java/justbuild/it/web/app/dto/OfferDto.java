package justbuild.it.web.app.dto;

import justbuild.it.web.app.entity.enums.ServiceCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static justbuild.it.web.app.entity.constants.AppConstants.DISPLAY_DATE_FORMAT;
import static justbuild.it.web.app.entity.constants.AppConstants.EMAIL_FORMAT;
import static justbuild.it.web.app.entity.constants.AppConstants.EXPIRY_DATE_FORMAT;

public class OfferDto {

    private Long dtoOfferId;
    private ServiceCategoryEnum serviceCategory;
    private String offerContent;
    @NotBlank
    private String city;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    @NotBlank
    private String userCompanyName;
    @Email(regexp = EMAIL_FORMAT)
    @NotBlank
    private String userEmailAddress;
    @NotBlank
    private String userTelephoneNumber;
    @DateTimeFormat(pattern = DISPLAY_DATE_FORMAT)
    private LocalDateTime dateTime;

    @DateTimeFormat(pattern = EXPIRY_DATE_FORMAT)
    private LocalDateTime expiryDate;

    public OfferDto() {
        this.dtoOfferId = 0L;
        this.serviceCategory = null;
        this.offerContent = "";
        this.city = "";
//        this.userId = 0L;
        this.userFirstName = "";
        this.userLastName = "";
        this.userCompanyName = "";
        this.userEmailAddress = "";
        this.userTelephoneNumber = "";
        this.dateTime = LocalDateTime.now();
        this.expiryDate = dateTime.plusDays(30);
    }

    public OfferDto(Long dtoOfferId, ServiceCategoryEnum serviceCategory, String offerContent, String city,
                    Long userId, String userFirstName, String userLastName, String userCompanyName, String userEmailAddress,
                    String userTelephoneNumber, LocalDateTime dateTime, LocalDateTime expiryDate) {
        this.dtoOfferId = dtoOfferId;
        this.serviceCategory = serviceCategory;
        this.offerContent = offerContent;
        this.city = city;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userCompanyName = userCompanyName;
        this.userEmailAddress = userEmailAddress;
        this.userTelephoneNumber = userTelephoneNumber;
        this.dateTime = dateTime;
        this.expiryDate = expiryDate;
    }

    public Long getDtoOfferId() {
        return dtoOfferId;
    }

    public void setDtoOfferId(Long dtoOfferId) {
        this.dtoOfferId = dtoOfferId;
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

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}