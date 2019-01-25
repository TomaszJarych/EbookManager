package tj.ebm.User.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tj.ebm.User.Service.UserService;
import tj.ebm.User.dto.UserDto;
import tj.ebm.commons.ENUM.UserRole;
import tj.ebm.commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.commons.Result.Result;
import tj.ebm.commons.SessionStorageData.SessionStorageData;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(path = "/user")
// @CrossOrigin(allowedHeaders="*")
public class UserController {

    private final UserService userService;
    private final SessionStorageData sessionData;

    @Autowired
    public UserController(UserService userService,
                          SessionStorageData sessionData) {
        this.userService = userService;
        this.sessionData = sessionData;
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getAllUsers() {
        return Result.ok(userService.getAll());
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Result saveNewUser(@Valid @RequestBody UserDto dto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
                    bindingResult.getFieldErrors()), dto);
        }

        return Result.ok(userService.save(dto));

    }

    @PostMapping(path = "/login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Result loginUser(@RequestBody UserDto dto) {
        UserDto loggedUser = userService.login(dto);
        if (loggedUser == null) {
            return Result.error("Invalid login or password");
        }

        sessionData.setLoggedUser(loggedUser);
        return Result.ok(loggedUser);
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getUserById(@PathVariable("id") Long id) {
        return Result.ok(userService.findById(id));
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public Result editUser(@Valid @RequestBody UserDto dto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
                    bindingResult.getFieldErrors()), dto);
        }
        return Result.ok(userService.save(dto));
    }

    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result deleteUser(@PathVariable("id") Long id) {
        return (userService.deleteFromDb(id))
                ? Result.ok("User has been deleted")
                : Result.error("Cannot delete user");
    }

    @GetMapping(path = "/setPrivilagesToADMIN/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result setUserRoleToAdmin(@PathVariable("id") Long id) {
        if (sessionData.getLoggedUser().getRole() == UserRole.ADMIN) {
            UserDto dto = userService.findById(id);
            dto.setRole(UserRole.ADMIN);
            return Result.ok(userService.save(dto));
        } else {
            return Result.error("You have no permission to change privilages");
        }
    }

    @GetMapping(path = "/setPrivilagesToUSER/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result setUserRoleToUser(@PathVariable("id") Long id) {
        if (sessionData.getLoggedUser().getRole() == UserRole.ADMIN) {
            UserDto dto = userService.findById(id);
            dto.setRole(UserRole.USER);
            return Result.ok(userService.save(dto));
        } else {
            return Result.error("You have no permission to change privilages");
        }
    }

    @GetMapping(path = "/getRecieversList", produces = APPLICATION_JSON_UTF8_VALUE)
    public Result getRecieversList() {
        if (sessionData.getLoggedUser().getId() == null) {
            return Result.error("There is no logged in User");
        }
        return Result.ok(userService.getAll().stream()
                .filter(el -> el.getId() != sessionData.getLoggedUser().getId())
                .collect(Collectors.toList()));

    }
}
