package justbuild.it.web.app.entity.constants;

public class AppConstants {

    public static final String OFFERS_FILEPATH = "offers.json";
    public static final String LOG_READ_FROM_FILE = "Error reading offers from file: ";
    public static final String LOG_WRITE_TO_FILE = "Error saving offers to file: ";
    public static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String DISPLAY_DATE_FORMAT = "'Oferta dodana lub zmieniona: ' dd MMM yyyy, HH:mm";
    public static final String EXPIRY_DATE_FORMAT = "'Wygaśnięcie oferty: ' dd MMM yyyy, HH:mm";
}

