package tj.ebm.User.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tj.ebm.Commons.ENUM.UserRole;
import tj.ebm.Commons.Validator.UniqueLogin;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

	private Long id;

	@UniqueLogin
	@NotBlank
	private String login;

	@NotBlank
	private String password;

	private String firstName;

	private String lastName;

	private UserRole role;

	@NotBlank
	private String email;

}
