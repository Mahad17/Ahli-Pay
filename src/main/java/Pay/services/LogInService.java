package Pay.services;

import Pay.model.Admin;
import Pay.model.User;
import Pay.repository.AdminRepository;
import Pay.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LogInService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Boolean logIn(String password,String username){
//String phoneNumber=countryCode + number;
        User find= userRepository.findByUserName(username);
        return passwordEncoder.matches(password,find.getPassword());
    }

    public User postDetails(User User) {

//        String countryCode= User.getCountryCode();
//        String phoneNumber=User.getNumber();
//        String phoneNumberMerge=countryCode + phoneNumber;
//        User.setPhoneNumber(phoneNumberMerge);
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encrypt= bCryptPasswordEncoder.encode(User.getPassword());
        User.setPassword(encrypt);
        return userRepository.save(User);
    }
    public Boolean logInAdmin(String password,String username){
//String phoneNumber=countryCode + number;
        Admin find= adminRepository.findByUserName(username);
        return passwordEncoder.matches(password,find.getPassword());
    }
    public Admin postDetailsAdmin(Admin admin) {

//        String countryCode= User.getCountryCode();
//        String phoneNumber=User.getNumber();
//        String phoneNumberMerge=countryCode + phoneNumber;
//        User.setPhoneNumber(phoneNumberMerge);
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encrypt= bCryptPasswordEncoder.encode(admin.getPassword());
        admin.setPassword(encrypt);
        return adminRepository.save(admin);
    }
    public void sendNotification(String deviceToken,String title,String body) {
        // Create a message
        

        Message message = Message.builder()
                .setNotification(new Notification("Title", "Your message body"))
                .setToken(deviceToken) // The device token of the recipient
                .build();

        // Send the message
        try {
            FirebaseMessaging.getInstance().send(message);
            System.out.println("Notification sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
        }
    }
}



