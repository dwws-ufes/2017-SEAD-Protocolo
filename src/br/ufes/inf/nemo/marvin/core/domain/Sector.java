package br.ufes.inf.nemo.marvin.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: document this type.
 *
 * @author Gustavo Monjardim (gustavomonjardim7@gmail.com)
 * @version 1.0
 */

@Entity
public class Sector extends PersistentObjectSupport implements Comparable<Sector> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	@Basic
	@NotNull
	private @Getter @Setter String name;
	
	@Basic
	private @Getter @Setter String description;
	
	@OneToMany(mappedBy="sector")
	private List<SectorAssociation> workers;
	
	@OneToMany(mappedBy="sector")
	private @Getter @Setter Set<Protocol> protocols;

	
	// Add an worker to the project.
	  // Create an association object for the relationship and set its data.
	  public void addWorker(User worker, String function) {
	    SectorAssociation association = new SectorAssociation();
	    association.setWorker(worker);
	    association.setSector(this);
	    association.setFunction(function);
	    if(this.workers == null)
	       this.workers = new ArrayList<>();

	    this.workers.add(association);
	    // Also add the association object to the worker.
	    worker.getSectors().add(association);
	  }


	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Sector o) {
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
