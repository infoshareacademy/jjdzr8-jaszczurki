package justbuild.it.web.app.model;

public class User {
    private String firstName;
    private String lastName;
    private String company;
    private String emailAddress;
    private String telephoneNumber;

    public User() {
        this.firstName = "";
        this.lastName = "";
        this.company = "";
        this.emailAddress = "";
        this.telephoneNumber = "";
    }

    public User(String firstName, String lastName, String company, String emailAddress, String telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.emailAddress = emailAddress;
        this.telephoneNumber = telephoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
