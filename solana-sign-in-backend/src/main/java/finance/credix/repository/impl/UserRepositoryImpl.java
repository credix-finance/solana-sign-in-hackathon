package finance.credix.repository.impl;

import finance.credix.entity.PublicKey;
import finance.credix.entity.User;
import finance.credix.repository.UserRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class UserRepositoryImpl implements UserRepository {
	private final EntityManager entityManager;

	public UserRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@TransactionalAdvice
	@Transactional
	@ReadOnly
	public Optional<User> findByPublicKey(String publicKey) {
		Query query = entityManager.createQuery("SELECT p FROM PublicKey p WHERE p.publicKey=:publicKey");
		query.setParameter("publicKey", publicKey);
		List<PublicKey> publicKeys = (List<PublicKey>) query.getResultList();
		if (!publicKeys.isEmpty()) {
			return Optional.ofNullable(publicKeys.get(0).getUser());
		}
		return Optional.empty();
	}

	@Override
	@ReadOnly
	public Optional<User> findById(UUID id) {
		return Optional.ofNullable(entityManager.find(User.class, id));
	}

	@Override
	@TransactionalAdvice
	@Transactional
	public User save(User user) {
		entityManager.persist(user);
		return user;
	}

	@Override
	@TransactionalAdvice
	@Transactional
	public void deleteById(UUID id) {
		findById(id).ifPresent(entityManager::remove);
	}

	@ReadOnly
	public List<User> findAll() {
		Query query = entityManager.createQuery("SELECT u FROM User u");
		return (List<User>) query.getResultList();
	}

	@Override
	@Transactional
	@TransactionalAdvice
	public void update(UUID id, User user) {
		User userToUpdate = entityManager.find(User.class, id);
		if (userToUpdate != null) {
			userToUpdate.setFirstName(user.getFirstName());
			userToUpdate.setLastName(user.getLastName());
			userToUpdate.setEmail(user.getEmail());
		} else {
			throw new RuntimeException("No such user available.");
		}
	}
}
