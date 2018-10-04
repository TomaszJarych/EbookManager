package tj.ebm.User.Service.Implementation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tj.ebm.Commons.DtoAndEntityConverter.DtoAndEntityConverter;
import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.Service.UserService;
import tj.ebm.User.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final DtoAndEntityConverter converter;

	@Autowired
	public UserServiceImpl(UserRepository userRepository,
			DtoAndEntityConverter converter) {
		this.userRepository = userRepository;
		this.converter = converter;
	}

	@Override
	public UserDto findById(Long id) {

		return converter.toUserDto(userRepository.getOne(id));
	}

	@Override
	public UserDto save(UserDto dto) {
		return converter
				.toUserDto(userRepository.save(converter.toUserEntity(dto)));
	}

	@Override
	public Boolean deleteFromDb(Long id) {
		try {
			userRepository.findById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<UserDto> getAll() {
		return toUserDtoList(userRepository.findAll());
	}

	private List<UserDto> toUserDtoList(List<User> list) {
		return list.stream().filter(Objects::nonNull).map(converter::toUserDto)
				.collect(Collectors.toList());
	}

}
