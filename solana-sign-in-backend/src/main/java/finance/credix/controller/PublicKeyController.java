package finance.credix.controller;

import finance.credix.entity.PublicKey;
import finance.credix.repository.PublicKeyRepository;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.util.List;
import java.util.Optional;

@Secured(SecurityRule.IS_AUTHENTICATED)
@ExecuteOn(TaskExecutors.IO)
@Controller("/api/publickeys")
public class PublicKeyController {

	protected final PublicKeyRepository publicKeyRepository;

	public PublicKeyController(PublicKeyRepository publicKeyRepository) {
		this.publicKeyRepository = publicKeyRepository;
	}

	@Get
	public List<PublicKey> getPublicKeys() {
		return publicKeyRepository.findAll();
	}

	@Get("/{publicKey}")
	public Optional<PublicKey> getPublicKey(String publicKey) {
		return publicKeyRepository.findByKey(publicKey);
	}

	@Post
	public PublicKey addPublicKey(Authentication authentication, @Body PublicKey publicKey) {
		String _publicKey = authentication.getAttributes().get("preferred_username").toString();
		return publicKeyRepository.saveByPublicKey(_publicKey, publicKey);
	}

	@Delete("/{publicKey}")
	public void deletePublicKey(String publicKey) {
		publicKeyRepository.deleteByKey(publicKey);
	}
}
