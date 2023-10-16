package br.com.joaoferreira.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
/*
 * Modificador
 * public
 * private
 * protected
 */

@RestController
@RequestMapping("/users")
public class UserController {
	/* 
	 * Tipos escopos de metodo, e o retorno dele
	 * String (texto)
	 * Integer(int) numeros inteiros
	 * Double (double) Numeros 0.0000
	 * Float (float) numeros 0.000
	 * char (A C)
	 * Date (date)
	 * void 
	 */
	/*
	 * As informações do create vao vir dentro do body do request
	 */
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/")
	public ResponseEntity create(@RequestBody UserModel userModel) {
		var user = this.userRepository.findByUsername(userModel.getUsername());
		
		if(user!= null) {
		
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Usuario ja existe");
		}
		var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
		userModel.setPassword(passwordHashred);
		var userCreated = this.userRepository.save(userModel);
		 return ResponseEntity.status(200).body(userCreated);
	}
}
