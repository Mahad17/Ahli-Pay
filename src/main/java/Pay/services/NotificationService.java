package Pay.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class NotificationService {
    public void sendNotificationToAdmin(String notificationMessage) {
        try {
            // Load Firebase credentials file
            FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            // Construct the FCM message
            Message message = Message.builder()
                    .putData("message", notificationMessage)
                    .setToken("your_admin_device_token_here") // Admin device token or topic
                    .build();

            // Send the message asynchronously
            FirebaseMessaging.getInstance().sendAsync(message);

            // Close the service account input stream
            serviceAccount.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions here
        }
    }
}
//    @PostConstruct
//    public void initialize() throws IOException {
//        FileInputStream serviceAccount =
//                new FileInputStream("Pay/serviceAccountKey.json"); // Replace with your file path
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);
//    }
//    @Autowired
//    private FirebaseMessaging firebaseMessaging;
//
//    public NotificationService(FirebaseMessaging firebaseMessaging) {
//        this.firebaseMessaging = firebaseMessaging;
//    }
//
//    public void sendNotificationToUser(String token,String userId, String title, String message) {
//        // Create a message
//        Message fcmMessage = Message.builder()
//                .setToken(token)
//                .setNotification(new Notification(userId,title, message))
//                .build();
//
//        // Send the message
//        try {
//            firebaseMessaging.send(fcmMessage);
//            // Notification sent successfully
//        } catch (FirebaseMessagingException e) {
//            // Handle exception if notification sending fails
//            e.printStackTrace();
//        }
//    }

