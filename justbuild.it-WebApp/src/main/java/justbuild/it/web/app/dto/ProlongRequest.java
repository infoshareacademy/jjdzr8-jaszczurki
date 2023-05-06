package justbuild.it.web.app.dto;

public class ProlongRequest {
    private Long id;
    private Integer days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
