package br.ufes.inf.nemo.marvin.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseJPADAO;
import br.ufes.inf.nemo.marvin.core.domain.Protocol;

/**
 * TODO: document this type.
 *
 * @author Wagner de Andrade Perin (wagnerperin@gmail.com)
 * @version 1.0
 */
@Stateless
public class ProtocolJPADAO extends BaseJPADAO<Protocol> implements ProtocolDAO {
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
