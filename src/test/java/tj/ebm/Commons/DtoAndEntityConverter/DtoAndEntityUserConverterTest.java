package tj.ebm.Commons.DtoAndEntityConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tj.ebm.Author.Repository.AuthorRepository;
import tj.ebm.Book.Repository.BookRepository;
import tj.ebm.Bookstore.Repository.BookstoreRepository;
import tj.ebm.Commons.ENUM.UserRole;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DtoAndEntityUserConverterTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BookstoreRepository bookstoreRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;

    private DtoAndEntityConverter converter;

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String email;
    private String encyptedPassword;
    private String salt;

    @BeforeEach
    void setUp() {
        converter = new DtoAndEntityConverter(userRepository, bookstoreRepository, genreRepository, authorRepository, bookRepository);
        id = 1L;
        login = "testLogin";
        password = "testpassword";
        salt = BCrypt.gensalt();
        encyptedPassword = BCrypt.hashpw(password, salt);
        firstName = "John";
        lastName = "Doe";
        email = "testmail@wp.pl";
        role = UserRole.USER;


    }

    @Test
    @Tag("toUserDto")
    void toUserDto() {
        //given
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(encyptedPassword);
        user.setRole(role);
        user.setEmail(email);

        UserDto actual = new UserDto();
        actual.setId(id);
        actual.setLogin(login);
        actual.setFirstName(firstName);
        actual.setLastName(lastName);
        actual.setPassword(null);
        actual.setRole(role);
        actual.setEmail(email);

        // when
        UserDto result = converter.toUserDto(user);

        //then
        assertAll("toUserDto converter test",
                () -> assertEquals(actual.getId(), result.getId()),
                () -> assertEquals(actual.getEmail(),result.getEmail()),
                () -> assertEquals(actual.getLogin(),result.getLogin()),
                () -> assertEquals(actual.getPassword(),result.getPassword()),
                () -> assertEquals(actual.getFirstName(),result.getFirstName()),
                () -> assertEquals(actual.getLastName(),result.getLastName()),
                () -> assertEquals(actual.getRole(),result.getRole())
        );

    }

    @Test
    @Tag("toUserEntity")
    void toUserEntityWithoutID() {

        //given
        UserDto dto = new UserDto();
        dto.setId(null);
        dto.setLogin(login);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);

        dto.setRole(role);
        dto.setEmail(email);

        User actual = new User();
        actual.setId(null);
        actual.setFirstName(firstName);
        actual.setLastName(lastName);
        actual.setLogin(login);
        actual.setRole(role);
        actual.setEmail(email);

        //when

       User result = converter.toUserEntity(dto);
        //then
        assertAll("toUserDto converter test",
                () -> assertEquals(actual.getId(), result.getId()),
                () -> assertEquals(actual.getEmail(),result.getEmail()),
                () -> assertEquals(actual.getLogin(),result.getLogin()),
                () -> assertEquals(actual.getPassword(),result.getPassword()),
                () -> assertEquals(actual.getFirstName(),result.getFirstName()),
                () -> assertEquals(actual.getLastName(),result.getLastName()),
                () -> assertEquals(actual.getRole(),result.getRole())
        );




    }
    @Test
    @Tag("toUserEntityWithDBMock")
    void toUserEntityWithIdAndMockingDB() {

        //given
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setLogin(login);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);

        dto.setRole(role);
        dto.setEmail(email);

        User stub = new User();
        stub.setId(id);

        Mockito.when(userRepository.getOne(Mockito.anyLong())).thenReturn(stub);

        User actual = new User();
        actual.setId(id);
        actual.setFirstName(firstName);
        actual.setLastName(lastName);
        actual.setLogin(login);
        actual.setRole(role);
        actual.setEmail(email);

        //when

       User result = converter.toUserEntity(dto);
        //then

        Mockito.verify(userRepository, Mockito.times(1)).getOne(id);

        assertAll("toUserDto converter test",
                () -> assertEquals(actual.getId(), result.getId()),
                () -> assertEquals(actual.getEmail(),result.getEmail()),
                () -> assertEquals(actual.getLogin(),result.getLogin()),
                () -> assertEquals(actual.getPassword(),result.getPassword()),
                () -> assertEquals(actual.getFirstName(),result.getFirstName()),
                () -> assertEquals(actual.getLastName(),result.getLastName()),
                () -> assertEquals(actual.getRole(),result.getRole())
        );




    }
}