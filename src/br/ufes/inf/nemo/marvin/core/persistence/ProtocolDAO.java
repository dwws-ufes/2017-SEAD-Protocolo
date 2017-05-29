package br.ufes.inf.nemo.marvin.core.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.marvin.core.domain.Protocol;

/**
 * TODO: document this type.
 *
 * @author Wagner de Andrade Perin (wagnerperin@gmail.com)
 * @version 1.0
 */
@Local
public interface ProtocolDAO extends BaseDAO<Protocol> {
	
}
