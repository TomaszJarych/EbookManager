package tj.ebm.User.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.ebm.Commons.ENUM.UserRole;
import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.Commons.Result.Result;
import tj.ebm.Commons.SessionStorageData.SessionStorageData;
import tj.ebm.User.Service.UserService;
import tj.ebm.User.dto.UserDto;

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
