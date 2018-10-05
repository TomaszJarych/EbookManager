package tj.ebm.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tj.ebm.Commons.ENUM.UserRole;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
	
	private Long id;
	
	@NonNull
	private String login;
	
	@NonNull
	private String password;
	
	@NonNull
	private String firstName;
	
	@NonNull
	private String lastName;
	
	@NonNull
	private UserRole role;
	
	@NonNull
	private String email;
	
	
	

}
