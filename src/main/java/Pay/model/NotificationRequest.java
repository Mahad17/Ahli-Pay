package Pay.model;

import lombok.Data;

@Data
public class NotificationRequest {
    private String userId;
    private String body;
    private String token;


}
