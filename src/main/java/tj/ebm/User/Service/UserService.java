package tj.ebm.User.Service;

import tj.ebm.User.dto.UserDto;
import tj.ebm.commons.ServiceInterface.BaseCrudService;

public interface UserService extends BaseCrudService<UserDto, Long> {

    UserDto login(UserDto dto);

}
