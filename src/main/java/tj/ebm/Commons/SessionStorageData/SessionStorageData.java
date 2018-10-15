package tj.ebm.Commons.SessionStorageData;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.ebm.User.dto.UserDto;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApplicationScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionStorageData {

	private UserDto loggedUser = new UserDto();

}
