package justbuild.it.web.app.entity.enums;

public enum ServiceCategoryEnum {
    CONSTRUCTION("Budowa"),
    FINISHING_WORKS("Remont"),
    INSTALLATION("Instalacje"),
    ELECTRICITY("Elektryka"),
    EARTH_WORKS("Roboty ziemne"),
    GARDEN("Ogród");

    final String polishName;

    ServiceCategoryEnum(String polishName) {
        this.polishName = polishName;
    }

    @Override
    public String toString() {
        return polishName;
    }
}