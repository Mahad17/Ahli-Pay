package Pay.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int messagesId;

    private String date;
    private String time;
    private String phoneNumber;
    private String messageBody;

//    @ManyToOne
//    @JoinColumn(name = "notificationId",nullable = false)
//    private NotificationData notificationId;


}
