package Pay.controller;

//import Pay.model.NotificationData;
import Pay.model.Admin;
import Pay.model.NotificationData;
import Pay.model.NotificationRequest;
//import Pay.repository.NotificationRepo;
//import Pay.services.NotificationService;
import Pay.model.Token;
import Pay.repository.AdminRepository;
import Pay.repository.NotificationRepo;
import Pay.response.ResponseHandler;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    AdminRepository adminRepository;
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
    @PostMapping("/refresh-token")
public ResponseHandler refreshtoken(@RequestBody Token token){
        String tokentorefresh=token.getToken();
        int id= Integer.parseInt(token.getAdminId());
        Admin admin= adminRepository.findById(id);
        if(admin==null){
            return new ResponseHandler(0,"no admin found");
        }else {
            admin.setToken(tokentorefresh);
            Admin adminSave= adminRepository.save(admin);
            return new ResponseHandler(1,"new Token saved",adminSave);

        }

    }
    @GetMapping("/all")
    public ResponseHandler allNotificationGet(){
        List<NotificationData> notificationDataList= notificationRepo.findAll();
if(notificationDataList.isEmpty()){
    return new ResponseHandler(0,"No notification yet");

}
else {
    return new ResponseHandler(1,"all notifications",notificationDataList);

}
    }

}
