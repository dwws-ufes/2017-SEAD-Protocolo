package br.ufes.inf.nemo.marvin.core.persistence;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.marvin.core.domain.Sector;

/**
 * TODO: document this type.
 *
 * @author Gustavo Monjardim (gustavomonjardim7@gmail.com)
 * @version 1.0
 */
@Local
public interface SectorDAO extends BaseDAO<Sector> {
	
}
