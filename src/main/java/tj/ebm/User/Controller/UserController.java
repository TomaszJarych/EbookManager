package tj.ebm.User.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.persistence.EntityNotFoundException;
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

import tj.ebm.Commons.ErrorsUtil.ErrorsUtil;
import tj.ebm.Commons.Result.Result;
import tj.ebm.User.Service.UserService;
import tj.ebm.User.dto.UserDto;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	private final UserService userService;
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getAllUsers() {
		return Result.ok(userService.getAll());
	}

	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result saveNewUser(@Valid @RequestBody UserDto dto,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
						bindingResult.getFieldErrors()), dto);
			}

			return Result.ok(userService.save(dto));

		} catch (EntityNotFoundException e) {
			return Result.error("Entity not found");
		}
	}

	@PostMapping(path = "/login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result loginUser(@RequestBody UserDto dto) {

		UserDto loggedUser = userService.login(dto);
		if (loggedUser == null) {
			return Result.error("Invalid login or password");
		}
		return Result.ok(true);
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getUserById(@PathVariable("id") Long id) {
		try {
			return Result.ok(userService.findById(id));
		} catch (EntityNotFoundException e) {
			return Result.error("Entity not found");
		}
	}

	@PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result editUser(@Valid @RequestBody UserDto dto,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
						bindingResult.getFieldErrors()), dto);
			}

			return Result.ok(userService.save(dto));

		} catch (EntityNotFoundException e) {
			return Result.error("Entity not found");
		}
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result deleteUser(@PathVariable("id") Long id) {
		return (userService.deleteFromDb(id))
				? Result.ok("User has been deleted")
				: Result.error("Cannot delete user");
	}

}
