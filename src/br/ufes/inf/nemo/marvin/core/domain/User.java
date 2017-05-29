package br.ufes.inf.nemo.marvin.core.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.marvin.people.domain.Person;
import br.ufes.inf.nemo.marvin.people.domain.Telephone;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */

@Entity
public class User extends Person {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Short name to use when there isn't much space. */
	@Basic
	@NotNull
	@Size(max = 30)
	@Column(unique=true)
	private @Getter @Setter String userName;

	/** Electronic address, which also serves as username for identification. */
	@Basic
	@Size(max = 100)
	private @Getter @Setter String email;

	/** The password, which identifies the user. */
	@Basic
	@Size(max = 32)
	private @Getter @Setter String password;

	/** Phone numbers. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private @Getter @Setter Set<Telephone> telephones;

	/** The timestamp of the moment this academic was created. */
	@Temporal(TemporalType.TIMESTAMP)
	private @Getter @Setter Date creationDate;

	/** The last time the data about the user was updated. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private @Getter @Setter Date lastUpdateDate;

	/** The last time the user logged in the system. */
	@Temporal(TemporalType.TIMESTAMP)
	private @Getter @Setter Date lastLoginDate;
	
	@Embedded
	private @Getter @Setter Address address = new Address();
	
	@Basic
	private @Getter @Setter UserTypes userType = UserTypes.REGULAR;
	
	@OneToMany(mappedBy="worker")
	private @Getter @Setter List<SectorAssociation> sectors;
	
	@OneToMany(mappedBy="user")
	private @Getter @Setter Set<Protocol> protocols;
	
}
