package com.mt.erp.settings.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/email/{email}/password/{password}")
	public ResponseEntity<User> getUser(@PathVariable String email, @PathVariable String password ) {
		User user = null;
		user = userRepository.findByEmailAndPassword(email,password);		
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> getUserInfo() {
		User user = null;
		if (userRepository.findAll().iterator().hasNext())
			user = userRepository.findAll().iterator().next();
		else {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id) {

		User userUpdated = null;
	
		User oldUser = userRepository.findById(id).orElseThrow();
		if (oldUser != null) {
			oldUser.setFirstName(user.getFirstName());
			oldUser.setLastName(user.getLastName());
			oldUser.setActivated(user.isActivated());
			oldUser.setPassword(user.getPassword());
			oldUser.setProfilImage(user.getProfilImage());
			//
			oldUser.getContact().setEmail(user.getContact().getEmail());
			//
			oldUser.getDetails().setLastUpdatedAt(new Date());
			userUpdated = userRepository.save(oldUser);
		}
		return ResponseEntity.ok(userUpdated);
	}
}
