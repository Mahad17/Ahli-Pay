package Pay.repository;

import Pay.model.NotificationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationData,Integer> {
    NotificationData findByUserId(String userId);

    List<NotificationData> findAllByUserId(String userId);
}
