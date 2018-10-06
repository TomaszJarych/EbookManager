package tj.ebm.Genre.Service.Implementation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tj.ebm.Commons.DtoAndEntityConverter.DtoAndEntityConverter;
import tj.ebm.Genre.Repository.GenreRepository;
import tj.ebm.Genre.Service.GenreService;
import tj.ebm.Genre.domain.Genre;
import tj.ebm.Genre.dto.GenreDto;

@Service
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;
	private final DtoAndEntityConverter converter;

	@Autowired
	public GenreServiceImpl(GenreRepository genreRepository,
			DtoAndEntityConverter converter) {
		this.genreRepository = genreRepository;
		this.converter = converter;
	}

	@Override
	public GenreDto findById(Long id) throws EntityNotFoundException {
		return converter.toGenreDto(genreRepository.getOne(id));
	}

	@Override
	public GenreDto save(GenreDto dto) {
		return converter
				.toGenreDto(genreRepository.save(converter.toGenreEntity(dto)));
	}

	@Override
	public Boolean deleteFromDb(Long id) {
		try {
			genreRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<GenreDto> getAll() {
		return toGenreDtoList(genreRepository.findAll());
	}

	private List<GenreDto> toGenreDtoList(List<Genre> list) {
		return list.stream().filter(Objects::nonNull).map(converter::toGenreDto)
				.collect(Collectors.toList());
	}

}
