package tj.ebm.Bookstore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookstoreDto {

	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@URL
	private String web;

}
