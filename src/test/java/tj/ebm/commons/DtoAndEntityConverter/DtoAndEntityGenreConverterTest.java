package tj.ebm.commons.DtoAndEntityConverter;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.Genre.dto.GenreDto;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DtoAndEntityGenreConverterTest {


    @Mock
    private GenreRepository genreRepository;

    private DtoAndEntityConverter converter;

    private Long id;
    private String name;
    private String description;

    @BeforeEach
    void setUp() {
        converter = new DtoAndEntityConverter(null, null, genreRepository,
                null, null);
        id = 1L;
        name = "testGenre";
        description = "testDesccription";
    }

    @Test
    @DisplayName("toGenreDto method test")
    void toGenreDtoConverterTest() {
        //given
        Genre genre = new Genre.GenreBuilder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .build();

        GenreDto expected = new GenreDto.GenreDtoBuilder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .build();

        //when
        GenreDto actual = converter.toGenreDto(genre);

        //then

        assertAll("toGenreDto() method test",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getDescription(), actual.getDescription()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }

    @Test
    @DisplayName("toGenreEntity method test without Db")
    void toGenreEntityTestWithoutMockingDb() {
        //given
        GenreDto dto = new GenreDto.GenreDtoBuilder()
                .setId(null)
                .setName(name)
                .setDescription(description)
                .build();

        Genre expected = new Genre.GenreBuilder()
                .setId(null)
                .setName(name)
                .setDescription(description)
                .build();

        //when

        Genre actual = converter.toGenreEntity(dto);
        //then

        assertAll("toGenreDto() method test",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getDescription(), actual.getDescription()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }

    @Test
    @DisplayName("toGenreEntity method test - Db mock")
    void toGenreEntityTestWithMockingDb() {
        //given
        GenreDto dto = new GenreDto.GenreDtoBuilder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .build();

        Genre expected = new Genre.GenreBuilder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .build();

        Genre stub = new Genre.GenreBuilder()
                .setId(id)
                .build();

        when(genreRepository.getOne(anyLong())).thenReturn(stub);

        //when

        Genre actual = converter.toGenreEntity(dto);
        //then

        Mockito.verify(genreRepository, atLeast(1)).getOne(id);

        assertAll("toGenreDto() method test",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getDescription(), actual.getDescription()),
                () -> assertEquals(expected.getName(), actual.getName())
        );
    }

    @Test
    @DisplayName("exception EntityNotFound throws")
    void toGenreEntityMethodTestWithEntityNotFoundException() {
        GenreDto dto = new GenreDto.GenreDtoBuilder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .build();
        Genre stub = new Genre.GenreBuilder()
                .setId(id)
                .build();
        Mockito.when(genreRepository.getOne(anyLong())).thenThrow(EntityNotFoundException.class);

        //then
        assertThrows(EntityNotFoundException.class, () -> converter.toGenreEntity(dto));
    }
}