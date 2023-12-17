package Pay.repository;

import Pay.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
//    Boolean findByNumber(String number);
    User findByUserName(String username);
    public boolean existsByUserName(String userName);

    User findById(int userId);
    public boolean existsByPhoneNumber(String phoneNumber);
    User findByPhoneNumber(String phoneNumber);

//    List<User> findAllUse();

//    boolean existsByNumber(String phoneNumber);
}
