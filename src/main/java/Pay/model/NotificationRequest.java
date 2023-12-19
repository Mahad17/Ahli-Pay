package Pay.model;

import lombok.Data;

@Data
public class NotificationRequest {
    private String userId;
    private String time;
    private String date;
    private String phoneNumber;
    private String body;
    private String token;


}
