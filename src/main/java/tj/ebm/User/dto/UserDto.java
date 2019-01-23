package tj.ebm.User.dto;

import lombok.*;
import tj.ebm.Commons.ENUM.UserRole;
import tj.ebm.Commons.Validator.UniqueLogin;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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
