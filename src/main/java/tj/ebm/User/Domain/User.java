package tj.ebm.User.Domain;

import lombok.*;
import tj.ebm.Commons.ENUM.UserRole;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
@Builder
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

}
