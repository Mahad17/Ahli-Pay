package Pay.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Service
//public class NotificationService {
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
//}
