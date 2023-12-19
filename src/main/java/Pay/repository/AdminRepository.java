package Pay.repository;

import Pay.model.Admin;
import Pay.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer>{
//    Boolean findByNumber(String number);
    Admin findById(int id);
    Admin findByUserName(String username);
    public boolean existsByUserName(String userName);

    public boolean existsByPhoneNumber(String phoneNumber);
    Admin findByPhoneNumber(String phoneNumber);

//    List<Admin> findAllWithToken();

//    List<Admin> findAllAdmin();

//    boolean existsByNumber(String phoneNumber);
}
