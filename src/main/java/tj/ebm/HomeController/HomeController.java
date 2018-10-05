package tj.ebm.HomeController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping("/hello")
	public String helloWorld() {

		return "{\"content\": \"Hello World!!!!!\"}";
	}

}
