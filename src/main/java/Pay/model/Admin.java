package Pay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Data
@NoArgsConstructor
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

//	@Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
//	@NotEmpty(message = "must not be empty")
	private String userName;

//	@Email(message="must be in proper format")
//	private String email;

//	@Pattern(regexp = "^[0-9]+$", message = "Must contain only numbers")
//	@NotEmpty(message = "must not be empty")
//	private String countryCode;

//	@Pattern(regexp="[0-9]{10}$", message = "10 digits only")
////	@Size(min=11,max = 13)
//	@NotEmpty(message = "must not be empty")
	private String phoneNumber;
	
//	@Pattern(regexp = "^(?!\\s)[A-Za-z\\d!@#$%^&*()_+{}:;<>,.?~\\-=|\\[\\]]{8,}$", message = "Password must be at least 8 characters long, contain at least one letter, one digit, and no spaces.")
//	@Size(min = 8, message = "Password must be at least 8 characters long")
////	@Pattern(regexp="^\\s+$\n",message = "No space in password.")
//	@NotEmpty(message = "must not be empty")
	private String password;

//	@Pattern(regexp = "^(?!\\s)[A-Za-z\\d!@#$%^&*()_+{}:;<>,.?~\\-=|\\[\\]]{8,}$", message = "Password must be at least 8 characters long, contain at least one letter, one digit, and no spaces.")
//	@Size(min = 8, message = "Password must be at least 8 characters long")
//	@Pattern(regexp="^\\s+$\n",message = "No space in password.")
//	@NotEmpty(message = "must not be empty")
	private String cprNumber;

	private String token;
}
