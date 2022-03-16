package finance.credix.repository.impl;

import finance.credix.entity.PublicKey;
import finance.credix.entity.User;
import finance.credix.repository.PublicKeyRepository;
import finance.credix.repository.UserRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class PublicKeyRepositoryImpl implements PublicKeyRepository {

	@Inject
	UserRepository userRepository;

	private final EntityManager entityManager;

	public PublicKeyRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@ReadOnly
	public Optional<PublicKey> findByKey(String publicKey) {
		return Optional.ofNullable(entityManager.find(PublicKey.class, publicKey));
	}

	@Override
	@TransactionalAdvice
	@Transactional
	public PublicKey save(PublicKey publicKey) {
		entityManager.persist(publicKey);
		return publicKey;
	}

	@Override
	public PublicKey saveByPublicKey(String _publicKey, PublicKey publicKey) {
		Optional<User> user = userRepository.findByPublicKey(_publicKey);
		if (user.isPresent()) {
			publicKey.setUser(user.get());
			user.get().addPublicKey(publicKey);
			save(publicKey);
		}
		return publicKey;
	}

	@Override
	@TransactionalAdvice
	@Transactional
	public void deleteByKey(String publicKey) {
		findByKey(publicKey).ifPresent(entityManager::remove);
	}

	@ReadOnly
	public List<PublicKey> findAll() {
		Query query = entityManager.createQuery("SELECT u FROM PublicKey u");
		return (List<PublicKey>) query.getResultList();
	}
}
