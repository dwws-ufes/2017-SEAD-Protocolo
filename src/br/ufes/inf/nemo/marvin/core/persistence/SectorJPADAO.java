package br.ufes.inf.nemo.marvin.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.marvin.core.domain.Sector;


/**
 * TODO: document this type.
 *
 * @author Gustavo Monjardim (gustavomonjardim7@gmail.com)
 * @version 1.0
 */
@Stateless
public class SectorJPADAO extends BaseJPADAO<Sector> implements SectorDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
