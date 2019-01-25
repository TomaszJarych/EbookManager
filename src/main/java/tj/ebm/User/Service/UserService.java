package tj.ebm.User.Service;

import tj.ebm.Commons.ServiceInterface.BaseCrudService;
import tj.ebm.User.dto.UserDto;

public interface UserService extends BaseCrudService<UserDto, Long> {

    UserDto login(UserDto dto);

}
