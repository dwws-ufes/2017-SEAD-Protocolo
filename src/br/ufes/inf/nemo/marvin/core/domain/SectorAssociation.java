package br.ufes.inf.nemo.marvin.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import lombok.Getter;
import lombok.Setter;

@Entity
public class SectorAssociation extends PersistentObjectSupport implements Comparable<SectorAssociation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562271484315049720L;

	@Column
	private @Getter @Setter String function;
	
	@ManyToOne
	@PrimaryKeyJoinColumn
	private @Getter @Setter User worker;
	
	@ManyToOne
	@PrimaryKeyJoinColumn
	private @Getter @Setter Sector sector;
	
	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(SectorAssociation o) {
		
		// If it's the same name, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return function;
	}
	
}
