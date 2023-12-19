package Pay.controller;

import Pay.model.Admin;
import Pay.model.NotificationData;
import Pay.model.User;
import Pay.repository.AdminRepository;
import Pay.repository.NotificationRepo;
import Pay.repository.UserRepository;
import Pay.response.ResponseHandler;
import Pay.services.LogInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pay")
public class LoginController {

	@Autowired
	LogInService logInService;

	@Autowired
	UserRepository repository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	NotificationRepo repo;

	@PostMapping(value = "/user-signUp",produces = "application/json")
	public ResponseHandler signUpUser(@RequestBody @Valid User user) {
		System.out.println(user);
		if(
				StringUtils.isEmpty(user.getCprNumber()) ||
						StringUtils.isEmpty(user.getPhoneNumber()) ||
//						StringUtils.isEmpty(user.getCountryCode()) ||
						StringUtils.isEmpty(user.getPassword()) ||
						StringUtils.isEmpty(user.getUserName()
						)){
			return new ResponseHandler(0, "Fields Are Empty");

		}

		User find=repository.findByUserName(user.getUserName());
		if(find!=null){
			return  new ResponseHandler(0,"name already exist");

		}
		String phoneNumberPattern= "^\\d{8}$";
		String phoneNumber = user.getPhoneNumber();
		if (!phoneNumber.matches(phoneNumberPattern)){
			return new ResponseHandler(0,"phone Number must be 8 digits");
		}

		boolean numberExists = repository.existsByPhoneNumber(phoneNumber);

		if (numberExists) {
			return new ResponseHandler(0, "Number already exists");
		}

		String userNamePattern = "^[a-zA-Z ]+$";
		if(!user.getUserName().matches(userNamePattern)){
			return new ResponseHandler(0,"No space, user name must contain only letters");
		}
		String passwordPattern="^(?!\\s)[A-Za-z\\d!@#$%^&*()_+{}:;<>,.?~\\-=|\\[\\]]{8,}$";
		if (!user.getPassword().matches(passwordPattern)){
			return new ResponseHandler(0, "Password length must be 8.");


		}
		String cprNumberPattern= "^\\d{9}$";
		String cprNumber=user.getCprNumber();
		if (!cprNumber.matches(cprNumberPattern)){
			return new ResponseHandler(0, "CPR Number must be at least 9 digits long.");

		}



		User post= logInService.postDetails(user);
		if (post!=null){
			return new ResponseHandler(1, "Signed Up successfully",post);

		}else {
			return new ResponseHandler(0, "Not Successful");
		}
	}
@PostMapping(value = "/user-login",produces = "application/json")
	public ResponseHandler logIn(@RequestBody User user){
		if (StringUtils.isEmpty(user.getUserName())|| StringUtils.isEmpty(user.getPassword())){
			return new ResponseHandler(0,"fields are empty");
		}


//		String phoneNumber= user.getCountryCode() + user.getNumber();
		boolean numberExists=repository.existsByUserName(user.getUserName());
		if (!numberExists){
			return new ResponseHandler(0,"User Not Registered");
		}
		else{
			Boolean isAuthenticated = logInService.logIn(user.getPassword(), user.getUserName());
			if (isAuthenticated) {
				User authenticate = repository.findByUserName(user.getUserName());
				return new ResponseHandler(1, "Login successful.", authenticate);
			} else {
				return new ResponseHandler(0, "Incorrect password.");
			}
		}
}

//admin signup
	@PostMapping(value = "/admin-signUp",produces = "application/json")
	public ResponseHandler signUpAdmin(@RequestBody @Valid Admin admin) {
		System.out.println(admin);
		if(
				StringUtils.isEmpty(admin.getCprNumber()) ||
						StringUtils.isEmpty(admin.getPhoneNumber()) ||
//						StringUtils.isEmpty(user.getCountryCode()) ||
						StringUtils.isEmpty(admin.getPassword()) ||
						StringUtils.isEmpty(admin.getUserName()
						)){
			return new ResponseHandler(0, "Fields Are Empty");

		}

		Admin find=adminRepository.findByUserName(admin.getUserName());
		if(find!=null){
			return  new ResponseHandler(0,"name already exist");

		}
		String phoneNumberPattern= "^\\d{8}$";
		String phoneNumber = admin.getPhoneNumber();
		if (!phoneNumber.matches(phoneNumberPattern)){
			return new ResponseHandler(0,"phone Number must be 8 digits");
		}

		boolean numberExists = adminRepository.existsByPhoneNumber(phoneNumber);

		if (numberExists) {
			return new ResponseHandler(0, "Number already exists");
		}

		String userNamePattern = "^[a-zA-Z ]+$";
		if(!admin.getUserName().matches(userNamePattern)){
			return new ResponseHandler(0,"No space, user name must contain only letters");
		}
		String passwordPattern="^(?!\\s)[A-Za-z\\d!@#$%^&*()_+{}:;<>,.?~\\-=|\\[\\]]{8,}$";
		if (!admin.getPassword().matches(passwordPattern)){
			return new ResponseHandler(0, "Password length must be 8.");


		}
		String cprNumberPattern= "^\\d{9}$";
		String cprNumber=admin.getCprNumber();
		if (!cprNumber.matches(cprNumberPattern)){
			return new ResponseHandler(0, "CPR Number must be at least 9 digits long.");

		}


		// Validate the phone number against the pattern
//		String phoneNo = admin.getPhoneNumber();
//		if (!phoneNo.matches(phonePattern)) {
//			return new ResponseHandler(0, "Invalid phone number format. Please use the format: +1-XXX-XXX-XXXX");
//		}
		Admin post= logInService.postDetailsAdmin(admin);
		if (post!=null){
			return new ResponseHandler(1, "Signed Up successfully",post);

		}else {
			return new ResponseHandler(0, "Not Successful");
		}
	}
	@PostMapping(value = "/admin-login",produces = "application/json")
	public ResponseHandler logInAdmin(@RequestBody Admin admin){
		if (StringUtils.isEmpty(admin.getUserName())|| StringUtils.isEmpty(admin.getPassword())|| StringUtils.isEmpty(admin.getToken())){
			return new ResponseHandler(0,"fields are empty");
		}


//		String phoneNumber= user.getCountryCode() + user.getNumber();
		boolean numberExists=adminRepository.existsByUserName(admin.getUserName());
		Admin adminFind=adminRepository.findByUserName(admin.getUserName());

		if (!numberExists){
			return new ResponseHandler(0,"Not Registered");
		}
		else{
			Boolean isAuthenticated = logInService.logInAdmin(admin.getPassword(), admin.getUserName());
			if (isAuthenticated) {
				Admin authenticate = adminRepository.findByUserName(admin.getUserName());
				adminFind.setToken(admin.getToken());
				return new ResponseHandler(1, "Login successful.", authenticate);
			} else {
				return new ResponseHandler(0, "Incorrect password.");
			}
		}
	}

@GetMapping(value = "/all-user",produces =  "application/json;charset=UTF-8")
	public ResponseHandler getAllUser(){
		List<User> findAllUser= (List<User>) repository.findAll();
		if(findAllUser.isEmpty()){
			return new ResponseHandler(0, "no user found.");
		}else{
			return new ResponseHandler(1, "These users have registered already.",findAllUser);
		}

}
@GetMapping(value="/getmessagesbyid/{userId}")
public ResponseEntity<?> getMessagesById(@PathVariable("userId") String userId) {
	List<NotificationData> dataList = repo.findAllByUserId(userId);

	if(dataList.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseHandler(0, "No user found"));
	} else if(dataList.size() == 1) {
		NotificationData data = dataList.get(0);
		return ResponseEntity.ok(new ResponseHandler(1, "User found", data));
	} else {
		List<ResponseHandler> responses = new ArrayList<>();
		for(NotificationData data : dataList) {
			responses.add(new ResponseHandler(1, "User found", data));
		}
		return ResponseEntity.ok(responses);
	}
}

//	@GetMapping(value="/getmessagesbyid/{userId}")
//public List<ResponseHandler> getMessagesById(@PathVariable("userId") String userId) {
//	List<NotificationData> dataList = repo.findAllByUserId(userId);
//	List<ResponseHandler> responses = new ArrayList<>();
//
//	if(dataList.isEmpty()) {
//		responses.add(new ResponseHandler(0, "No user found"));
//	} else {
//		for(NotificationData data : dataList) {
//			responses.add(new ResponseHandler(1, "User found", data));
//		}
//	}
//	return responses;
//}
}



