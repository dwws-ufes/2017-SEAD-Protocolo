package br.ufes.inf.nemo.marvin.people.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import lombok.Getter;
import lombok.Setter;

/**
 * Domain class that represents telephone numbers.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class Telephone extends PersistentObjectSupport implements Comparable<Telephone> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The number. */
	@Basic
	@NotNull
	@Size(max = 25)
	protected @Getter @Setter String number;

	/** The type of contact. */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@NotNull
	protected @Getter @Setter ContactType type = new ContactType();

	
	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Telephone o) {
		// First, order by contact type.
		if (type == null) return 1;
		if ((o == null) || (o.type == null)) return -1;
		int cmp = type.compareTo(o.type);
		if (cmp != 0) return cmp;

		// If it's the same, order by phone number.
		if (number == null) return 1;
		if (o.number == null) return -1;
		cmp = number.compareTo(o.number);
		if (cmp != 0) return cmp;

		// If it's the same type and number, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return number + " (" + type + ")";
	}
}
