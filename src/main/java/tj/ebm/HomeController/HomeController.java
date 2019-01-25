package tj.ebm.HomeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tj.ebm.User.dto.UserDto;
import tj.ebm.commons.Result.Result;
import tj.ebm.commons.SessionStorageData.SessionStorageData;

@RestController
@RequestMapping("/")
public class HomeController {

    private SessionStorageData sessionStorageData;

    @Autowired
    public HomeController(SessionStorageData sessionStorageData) {
        this.sessionStorageData = sessionStorageData;
    }

    @GetMapping("/logout")
    public Result logOutUser() {
        sessionStorageData.setLoggedUser(new UserDto());
        return Result.ok("User has been logged out");
    }

}
