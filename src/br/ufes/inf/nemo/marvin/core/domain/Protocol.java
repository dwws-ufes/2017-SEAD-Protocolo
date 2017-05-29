package br.ufes.inf.nemo.marvin.core.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: document this type.
 *
 * @author Wagner de Andrade Perin (wagnerperin@gmail.com)
 * @version 1.0
 */

@Entity
public class Protocol extends PersistentObjectSupport implements Comparable<Protocol> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6131306854122890195L;

	@Basic
	@NotNull
	private @Getter @Setter String description;

	@Basic
	@NotNull
	private @Getter @Setter ProtocolStatus callStatus = ProtocolStatus.OPEN;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private @Getter @Setter Date openDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private @Getter @Setter Date lastMoviment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Null
	private @Getter @Setter Date closeDate;
	
	@Basic
	@Null
	private @Getter @Setter String closeMessage;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@NotNull
	private @Getter @Setter User user;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@NotNull
	private @Getter @Setter Sector sector;
	
	
	@Override
	public int compareTo(Protocol o) {
	
		if (description == null) return 1;
		if (o.description == null) return -1;
		int cmp = description.compareTo(o.description);
		if (cmp != 0) return cmp;
	
		// If it's the same description, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}
	
}
