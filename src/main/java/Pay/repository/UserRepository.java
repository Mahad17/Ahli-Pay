package Pay.repository;

import Pay.model.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserData, Integer>{
//    Boolean findByNumber(String number);
    UserData findByUserName(String username);
    public boolean existsByUserName(String userName);

    UserData findById(int userId);
    public boolean existsByPhoneNumber(String phoneNumber);
    UserData findByPhoneNumber(String phoneNumber);

//    List<UserData> findAllUse();

//    boolean existsByNumber(String phoneNumber);
}
