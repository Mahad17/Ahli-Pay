package Pay.repository;

import Pay.model.Messages;
import Pay.model.NotificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages,Integer> {
//    Messages findByUserId(String userId);
}
