package Pay.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class NotificationData {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int notificationId;

        private String userId;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tutorial_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Tutorial tutorial;

//    @ElementCollection


//    private ArrayList<Messages> messages; // Assuming message is a String here, adjust as needed

        @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "messageId",referencedColumnName = "messageId")
        private List<Messages> messagesList;

    //@OneToMany(mappedBy = "course")
//	@JoinColumn(name = "CS_FK",referencedColumnName = "courseId")
    //private List<Student> students = new ArrayList<>();
//    public int getNotificationId() {
//            return notificationId;
//    }
//
//        public int setNotificationId(int notificationId) {
//                this.notificationId = notificationId;
//            return notificationId;
//        }
//
//        public String getUserId() {
//                return userId;
//        }
//
//        public void setUserId(String userId) {
//                this.userId = userId;
//        }
//
//        public List<Messages> getMessagesList() {
//                return messagesList;
//        }
//
//        public void setMessagesList(List<Messages> messagesList) {
//                this.messagesList = messagesList;
//        }
}
