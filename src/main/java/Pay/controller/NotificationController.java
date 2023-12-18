package Pay.controller;

//import Pay.model.NotificationData;
import Pay.model.*;
//import Pay.repository.NotificationRepo;
//import Pay.services.NotificationService;
import Pay.repository.AdminRepository;
import Pay.repository.MessagesRepository;
import Pay.repository.NotificationRepo;
import Pay.response.ResponseHandler;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    MessagesRepository messagesRepository;

    @PostMapping("/save")
    public ResponseEntity<Object> saveNotificationData(@RequestBody NotificationData notificationData) {
        if (StringUtils.isEmpty(notificationData.getUserId())) {
            return ResponseEntity.badRequest().body(new ResponseHandler(0, "Fields are empty"));
        }

        Optional<NotificationData> existingData = Optional.ofNullable(notificationRepo.findByUserId(notificationData.getUserId()));

        if (existingData.isPresent()) {
            // If NotificationData with given userId exists, update the messagesList
            NotificationData savedData = existingData.get();

            if (notificationData.getMessagesList() != null) {
                if (savedData.getMessagesList() == null) {
                    savedData.setMessagesList(new ArrayList<>());
                }
                savedData.getMessagesList().addAll(notificationData.getMessagesList());
            }

            savedData = notificationRepo.save(savedData);
            if (savedData != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseHandler(1, "Data updated", savedData));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseHandler(0, "Failed to update"));
            }
        } else {
            // If NotificationData with given userId does not exist, create a new entry
            NotificationData savedData = notificationRepo.save(notificationData);

            if (savedData != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseHandler(1, "Data saved", savedData));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseHandler(0, "Failed to save"));
            }
        }
    }

//@PostMapping("/save")
//public ResponseEntity<Object> saveNotificationData(@RequestBody NotificationData notificationData) {
//    if (StringUtils.isEmpty(notificationData.getUserId()) ) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseHandler(0, "Fields are empty"));
//    }
//
//    NotificationData savedData = notificationRepo.save(notificationData);
////   Messages messages=new Messages();
//    if (savedData != null) {
////        int notificationId = savedData.getNotificationId();
////        messages.setNotificationId(savedData.getNotificationId());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseHandler(1, "Data saved", savedData));
//    } else {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseHandler(0, "Failed to save"));
//    }
//}

    //    @PostMapping("/save")
//    public ResponseHandler saveNotificationData(@RequestBody NotificationData notificationData) {
//        // Process and save the received data
//        if(StringUtils.isEmpty(notificationData.getUserId())||notificationData.getMessagesList()==null){
//            return new ResponseHandler(0,"fields are empty");
//        }
//            NotificationData data= notificationRepo.save(notificationData);
//        if(data!=null) {
//            return new ResponseHandler(1, "data saved", data);
//        }else{
//            return new ResponseHandler(0,"not saved");
//
//        }
//    }
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
