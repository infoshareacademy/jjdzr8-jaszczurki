package justbuild.it.web.app.dto;

import justbuild.it.web.app.entity.enums.ServiceCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static justbuild.it.web.app.entity.constants.AppConstants.DISPLAY_DATE_FORMAT;
import static justbuild.it.web.app.entity.constants.AppConstants.EMAIL_FORMAT;
import static justbuild.it.web.app.entity.constants.AppConstants.PASSWORD_FORMAT;

public class OfferDto {

    private Long dtoOfferId;
    private ServiceCategoryEnum serviceCategory;
    private String offerContent;
    @NotBlank
    private String city;
    private String userFirstName;
    private String userLastName;
    @NotBlank
    private String userCompanyName;
    @Email(regexp = EMAIL_FORMAT)
    @NotBlank
    private String userEmailAddress;
    @Pattern(regexp = PASSWORD_FORMAT)
    @NotBlank
    private String userPassword;
    @NotBlank
    private String userTelephoneNumber;
    @DateTimeFormat(pattern = DISPLAY_DATE_FORMAT)
    private LocalDateTime dateTime;

    public OfferDto() {
        this.dtoOfferId = 0L;
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

    public OfferDto(Long dtoOfferId, ServiceCategoryEnum serviceCategory, String offerContent, String city,
                    String userFirstName, String userLastName, String userCompanyName, String userEmailAddress,
                    String userTelephoneNumber, LocalDateTime dateTime) {
        this.dtoOfferId = dtoOfferId;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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
