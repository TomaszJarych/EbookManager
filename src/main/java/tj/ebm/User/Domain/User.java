package tj.ebm.User.Domain;

import lombok.*;
import tj.ebm.commons.ENUM.UserRole;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NonNull
    private String email;


    public static class UserBuilder {
        private Long id;
        private String login;
        private String password;
        private String firstName;
        private String lastName;
        private UserRole role;
        private String email;

        public UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder role(UserRole role) {
            this.role = role;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(id, login, password, firstName, lastName, role, email);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", login=" + this.login + ", password=" + this.password + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", role=" + this.role + ", email=" + this.email + ")";
        }
    }
}
