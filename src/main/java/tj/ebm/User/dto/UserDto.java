package tj.ebm.User.dto;

import lombok.*;
import tj.ebm.commons.ENUM.UserRole;
import tj.ebm.commons.Validator.UniqueLogin;

import javax.validation.constraints.NotBlank;

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


    public static class UserDtoBuilder {
        private Long id;
        private String login;
        private String password;
        private String firstName;
        private String lastName;
        private UserRole role;
        private String email;

        public UserDtoBuilder() {
        }

        public UserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDtoBuilder role(UserRole role) {
            this.role = role;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, login, password, firstName, lastName, role, email);
        }
    }
}
