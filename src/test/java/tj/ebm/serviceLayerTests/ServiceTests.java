package tj.ebm.serviceLayerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tj.ebm.Author.Repository.AuthorRepository;
import tj.ebm.Book.Repository.BookRepository;
import tj.ebm.Bookstore.Repository.BookstoreRepository;
import tj.ebm.Bookstore.Service.BookstoreService;
import tj.ebm.Bookstore.Service.Implementation.BookstoreImpl;
import tj.ebm.Bookstore.domain.Bookstore;
import tj.ebm.Bookstore.dto.BookstoreDto;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.Genre.Service.GenreService;
import tj.ebm.Genre.Service.Implementation.GenreServiceImpl;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.Genre.dto.GenreDto;
import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.Service.Implementation.UserServiceImpl;
import tj.ebm.User.Service.UserService;
import tj.ebm.User.dto.UserDto;
import tj.ebm.commons.DtoAndEntityConverter.DtoAndEntityConverter;
import tj.ebm.commons.ENUM.UserRole;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {

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

    private UserService userService;
    private BookstoreService bookstoreService;
    private GenreService genreService;

    private User user1;
    private User user2;
    private UserDto userDto1;
    private UserDto userDto2;
    private Bookstore bookstore1;
    private Bookstore bookstore2;
    private BookstoreDto bookstoreDto1;
    private BookstoreDto bookstoreDto2;
    private Genre genre1;
    private Genre genre2;
    private GenreDto genreDto1;
    private GenreDto genreDto2;


    @BeforeEach
    void setUp() {
        converter = new DtoAndEntityConverter(userRepository, bookstoreRepository, genreRepository,
                authorRepository, bookRepository);
        userService = new UserServiceImpl(userRepository, converter);
        bookstoreService = new BookstoreImpl(bookstoreRepository, converter);
        genreService = new GenreServiceImpl(genreRepository, converter);
        user1 = new User.UserBuilder()
                .id(null)
                .firstName("John")
                .lastName("Doe")
                .email("testmail1@gmail.com")
                .login("testlogin1")
                .role(UserRole.USER)
                .password("pass")
                .build();
        userDto1 = new UserDto.UserDtoBuilder()
                .id(null)
                .firstName("John")
                .lastName("Doe")
                .email("testmail1@gmail.com")
                .login("testlogin1")
                .role(UserRole.USER)
                .build();
        user2 = new User.UserBuilder()
                .id(1L)
                .firstName("Martin")
                .lastName("Slick")
                .email("testmail1@wp.com")
                .login("testlogin2")
                .password("pass")
                .role(UserRole.ADMIN)
                .build();
        userDto2 = new UserDto.UserDtoBuilder()
                .id(1L)
                .firstName("Martin")
                .lastName("Slick")
                .email("testmail1@wp.com")
                .login("testlogin2")
                .role(UserRole.ADMIN)
                .build();
        bookstore1 = new Bookstore.BookstoreEntityBuilder()
                .setId(null)
                .setWeb("https://bookstore.pl")
                .setEmail("testmail@mail.com")
                .setName("bookstore")
                .build();
        bookstoreDto1 = new BookstoreDto.BookstoreDtoBuilder()
                .setId(null)
                .setWeb("https://bookstore.pl")
                .setEmail("testmail@mail.com")
                .setName("bookstore")
                .build();
        bookstore2 = new Bookstore.BookstoreEntityBuilder()
                .setId(null)
                .setWeb("https://bookstore.com")
                .setEmail("testdsdsmail@mail.com")
                .setName("bookstoresdsds")
                .build();
        bookstoreDto2 = new BookstoreDto.BookstoreDtoBuilder()
                .setId(null)
                .setWeb("https://bookstore.com")
                .setEmail("testdsdsmail@mail.com")
                .setName("bookstoresdsds")
                .build();
        genre1 = new Genre.GenreBuilder()
                .setId(null)
                .setDescription("testDescription")
                .setName("testName")
                .build();
        genreDto1 = new GenreDto.GenreDtoBuilder()
                .setId(null)
                .setDescription("testDescription")
                .setName("testName")
                .build();
        genre2 = new Genre.GenreBuilder()
                .setId(null)
                .setDescription("otherDescription")
                .setName("otherName")
                .build();
        genreDto2 = new GenreDto.GenreDtoBuilder()
                .setId(null)
                .setDescription("otherDescription")
                .setName("otherName")
                .build();

    }

    @Test
    void userServiceToListConverter() throws Exception {
        //given
        List<User> users = Arrays.asList(user1, user2);

        List<UserDto> expected = Arrays.asList(userDto1, userDto2);

        Method method = userService.getClass().getDeclaredMethod("toUserDtoList", List.class);
        method.setAccessible(true);

        //when
        List<UserDto> actual = (List<UserDto>) method.invoke(userService, users);

        //then

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getLogin(), actual.get(i).getLogin());
            assertEquals(expected.get(i).getEmail(), actual.get(i).getEmail());
            assertEquals(expected.get(i).getRole(), actual.get(i).getRole());
            assertEquals(expected.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(expected.get(i).getLastName(), actual.get(i).getLastName());
        }
    }

    @Test
    void toBookstoreDtoListTest() throws Exception {
        //given
        List<Bookstore> bookstores = Arrays.asList(bookstore1, bookstore2);
        List<BookstoreDto> expected = Arrays.asList(bookstoreDto1, bookstoreDto2);
        Method method = bookstoreService.getClass().getDeclaredMethod("toBookstoreDtoList", List.class);
        method.setAccessible(true);
        //when
        List<BookstoreDto> actual = (List<BookstoreDto>) method.invoke(bookstoreService, bookstores);
        //then
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
            assertEquals(expected.get(i).getEmail(), actual.get(i).getEmail());
            assertEquals(expected.get(i).getWeb(), actual.get(i).getWeb());
        }
    }

    @Test
    void toGenreDtoListMethodTest() {
        //given
        List<Genre> genres = Arrays.asList(genre1, genre2);
        List<GenreDto> expected = Arrays.asList(genreDto1, genreDto2);
        //when
        List<GenreDto> actual = (List<GenreDto>) getPrivateMethod(genreService, "toGenreDtoList", genres);
        //then
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
            assertEquals(expected.get(i).getDescription(), actual.get(i).getDescription());
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
        }
    }

    private Object getPrivateMethod(Object service, String methodName, Object arguments) {
        try {
            Method method = service.getClass().getDeclaredMethod(methodName, List.class);
            method.setAccessible(true);
            return method.invoke(service, arguments);
        } catch (Exception e) {
            return null;
        }
    }
}
