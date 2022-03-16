package finance.credix.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;

	@OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JsonIgnoreProperties("user")
	private Set<PublicKey> publicKeys;

	public User(UUID id, String firstName, String lastName, String emailId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = emailId;
	}

	public User() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<PublicKey> getPublicKeys() {
		return publicKeys;
	}

	public void setPublicKeys(Set<PublicKey> publicKeys) {
		this.publicKeys = publicKeys;
	}

	public void addPublicKey(PublicKey publicKey) {
		getPublicKeys().add(publicKey);
		publicKey.setUser(this);
	}
}
