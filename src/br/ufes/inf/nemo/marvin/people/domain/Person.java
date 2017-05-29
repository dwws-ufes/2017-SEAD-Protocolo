package br.ufes.inf.nemo.marvin.people.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import lombok.Getter;
import lombok.Setter;

/**
 * Domain class that represents people and their most basic attributes, such as name, birthdate and gender.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@MappedSuperclass
public class Person extends PersistentObjectSupport implements Comparable<Person> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The person's name. */
	@Basic
	@NotNull
	@Size(max = 100)
	protected @Getter @Setter String name;

	/** The person's birth date. */
	@Temporal(TemporalType.DATE)
	protected @Getter @Setter Date birthDate;

	/** The person's gender: 'M' (male) or 'F' (female). */
	@Basic
	protected @Getter @Setter Character gender;
	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Person o) {
		// Compare the persons' names
		if (name == null) return 1;
		if (o.name == null) return -1;
		int cmp = name.compareTo(o.name);
		if (cmp != 0) return cmp;

		// If it's the same name, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return name;
	}
}
