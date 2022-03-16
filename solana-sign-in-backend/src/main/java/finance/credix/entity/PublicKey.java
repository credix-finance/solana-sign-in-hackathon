package finance.credix.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "publickeys")
public class PublicKey implements Serializable {
	private static final long serialVersionUID = 2L;

	@Id
	private String publicKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties("publicKeys")
	private User user;

	public PublicKey(String publicKey) {
		super();
		this.publicKey = publicKey;
	}

	public PublicKey() {
		super();
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
