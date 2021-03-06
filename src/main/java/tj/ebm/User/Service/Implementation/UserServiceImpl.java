package tj.ebm.User.Service.Implementation;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.ebm.User.Domain.User;
import tj.ebm.User.Repository.UserRepository;
import tj.ebm.User.Service.UserService;
import tj.ebm.User.dto.UserDto;
import tj.ebm.commons.DtoAndEntityConverter.DtoAndEntityConverter;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        try {
            return converter.toUserDto(userRepository.getOne(id));

        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public UserDto save(UserDto dto) {
        return converter
                .toUserDto(userRepository.save(converter.toUserEntity(dto)));
    }

    @Override
    public Boolean deleteFromDb(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDto login(UserDto dto) {
        if (dto.getLogin() == null || dto.getLogin() == "") {
            return null;
        }
        User user = userRepository.findUserByLogin(dto.getLogin());
        if (Objects.isNull(user)) {
            return null;
        }
        if (BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            return converter.toUserDto(user);
        }
        return null;
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
