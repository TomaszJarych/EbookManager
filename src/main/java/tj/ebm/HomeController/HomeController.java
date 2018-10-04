package pl.coderslab.HomeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.coderslab.User.Service.Implementation.UserServiceImpl;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserServiceImpl service;

	@GetMapping("/hello")
	public String helloWorld() {

		return "Hello World!";
	}
}
