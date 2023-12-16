package Pay.controller;

//import Pay.model.NotificationData;
import Pay.model.NotificationData;
import Pay.model.NotificationRequest;
//import Pay.repository.NotificationRepo;
//import Pay.services.NotificationService;
import Pay.repository.NotificationRepo;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @PostMapping("/send-notification")
    public String sendNotification(@RequestBody NotificationRequest notificationRequest) {
        // Initialize Firebase Admin SDK (ensure you've downloaded the service account JSON file)
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

        // Create a Message object with notification payload
        Message message = Message.builder()
                .setToken(notificationRequest.getToken())
                .setNotification(new com.google.firebase.messaging.Notification(
                        notificationRequest.getUserId(),
                        notificationRequest.getBody()))
                .build();

        try {
            // Send the message
            String response = firebaseMessaging.send(message);
            return "Notification sent successfully: " + response;
        } catch (FirebaseMessagingException e) {
            return "Error sending notification: " + e.getMessage();
        }
    }

    @Autowired
    NotificationRepo notificationRepo;
//    @Autowired
//    NotificationService notificationService;
    @PostMapping("/save")
    public ResponseEntity<String> saveNotificationData(@RequestBody NotificationData notificationData) {
        // Process and save the received data
        try {
            notificationRepo.save(notificationData);
            return ResponseEntity.ok("Data saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving data.");
        }
    }
//
//    @PostMapping("/send")
//    public ResponseEntity<String> sendNotificationToAdmin(
//            @RequestParam String adminToken,
//            @RequestParam String title,
//            @RequestParam String message) {
//
//        // Send notification to the admin
//        try {
//            notificationService.sendNotificationToUser(adminToken, title, message);
//            return ResponseEntity.ok("Notification sent successfully to admin.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending notification to admin.");
//        }
//    }
}
