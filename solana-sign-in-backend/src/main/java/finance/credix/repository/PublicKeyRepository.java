package finance.credix.repository;

import finance.credix.entity.PublicKey;

import java.util.List;
import java.util.Optional;

public interface PublicKeyRepository {
	Optional<PublicKey> findByKey(String publicKey);

	PublicKey save(PublicKey publicKey);

	PublicKey saveByPublicKey(String _publicKey, PublicKey publicKey);

	void deleteByKey(String publicKey);

	List<PublicKey> findAll();
}
