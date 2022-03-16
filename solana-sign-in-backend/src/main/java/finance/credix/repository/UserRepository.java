package finance.credix.repository;

import finance.credix.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
	Optional<User> findByPublicKey(String publicKey);

	Optional<User> findById(UUID id);

	User save(User user);

	void deleteById(UUID id);

	List<User> findAll();

	void update(UUID id, User user);
}
