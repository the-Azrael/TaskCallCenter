import java.time.LocalDateTime;

public class Call {
    private String number;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Call(String number) {
        this.number = number;
        this.startDateTime = LocalDateTime.now();
    }

    public Call(String number, LocalDateTime dateTime) {
        this.number = number;
        this.startDateTime = dateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public String getNumber() {
        return number;
    }

    public void resolve() {
        setEndDateTime(LocalDateTime.now());
    }
}
