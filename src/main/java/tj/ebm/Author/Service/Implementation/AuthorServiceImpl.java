package tj.ebm.Author.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.ebm.Author.Repository.AuthorRepository;
import tj.ebm.Author.Service.AuthorService;
import tj.ebm.Author.domain.Author;
import tj.ebm.Author.dto.AuthorDto;
import tj.ebm.Commons.DtoAndEntityConverter.DtoAndEntityConverter;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final DtoAndEntityConverter converter;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository,
                             DtoAndEntityConverter converter) {
        this.authorRepository = authorRepository;
        this.converter = converter;
    }

    @Override
    public AuthorDto findById(Long id) throws EntityNotFoundException {
        return converter.toAuthorDto(authorRepository.getOne(id));
    }

    @Override
    public AuthorDto save(AuthorDto dto) {
        return converter.toAuthorDto(
                authorRepository.save(converter.toAuthorEntity(dto)));
    }

    @Override
    public Boolean deleteFromDb(Long id) {
        try {
            authorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<AuthorDto> getAll() {
        return toAuthorDtoList(authorRepository.findAll());
    }

    private List<AuthorDto> toAuthorDtoList(List<Author> list) {
        return list.stream().filter(Objects::nonNull)
                .map(converter::toAuthorDto).collect(Collectors.toList());
    }

}
