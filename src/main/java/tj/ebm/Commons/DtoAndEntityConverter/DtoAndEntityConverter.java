package tj.ebm.Commons.DtoAndEntityConverter;

import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.dto.UserDto;

@Component
public class DtoAndEntityConverter {

	private final UserRepository userRepository;

	@Autowired
	public DtoAndEntityConverter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDto toUserDto(User user) {
		UserDto dto = new UserDto();

		dto.setId(user.getId());
		dto.setLogin(user.getLogin());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());

		return dto;
	}

	public User toUserEntity(UserDto dto) throws EntityNotFoundException {
		User user;

		if (Objects.nonNull(dto.getId())) {
			user = userRepository.getOne(dto.getId());

		} else {
			user = new User();
		}

		user.setId(dto.getId());
		user.setLogin(dto.getLogin());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setRole(dto.getRole());

		if (dto.getPassword() != null && dto.getPassword() != "") {
			user.setPassword(
					BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
		}

		return user;
	}

}
