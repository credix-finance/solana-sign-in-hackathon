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
		String _publicKey = authentication.getAttributes().get("preferred_username").toString();
		return userRepository.findByPublicKey(_publicKey);
	}

	@Put("/me")
	public User updateMe(Authentication authentication, @Body User user) {
		String _publicKey = authentication.getAttributes().get("preferred_username").toString();
		Optional<User> _user = userRepository.findByPublicKey(_publicKey);
		if (_user.isPresent()) {
			return userRepository.update(_user.get().getId(), user);
		}
		return user;
	}

	@Post
	public User createUser(Authentication authentication, @Body User user) {
		String _publicKey = authentication.getAttributes().get("preferred_username").toString();
		Optional<User> _user = userRepository.findByPublicKey(_publicKey);
		if (_user.isPresent()) {
			return _user.get();
		}

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
