package finance.credix.controller;

import finance.credix.entity.PublicKey;
import finance.credix.entity.User;
import finance.credix.repository.UserRepository;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Secured(SecurityRule.IS_AUTHENTICATED)
@ExecuteOn(TaskExecutors.IO)
@Controller("/api/users")
public class UserController {

	protected final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Get
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Get("/me")
	public Optional<User> getMe(Authentication authentication) {
		String publicKey = authentication.getName();
		return userRepository.findByPublicKey(publicKey);
	}

	@Put("/me")
	public void updateMe(Authentication authentication, @Body User user) {
		String publicKey = authentication.getName();
		Optional<User> _user = userRepository.findByPublicKey(publicKey);
		_user.ifPresent(value -> userRepository.update(value.getId(), user));
	}

	@Post
	public User createUser(Authentication authentication, @Body User user) {
		String _publicKey = authentication.getName();
		PublicKey publicKey = new PublicKey();
		publicKey.setPublicKey(_publicKey);
		publicKey.setUser(user);
		user.setPublicKeys(Collections.singleton(publicKey));
		return userRepository.save(user);
	}

	@Get("/{id}")
	public Optional<User> getUser(UUID id) {
		return userRepository.findById(id);
	}

	@Put("/{id}")
	public void updateUser(UUID id, @Body User user) {
		userRepository.update(id, user);
	}

	@Delete("/{id}")
	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}
}
