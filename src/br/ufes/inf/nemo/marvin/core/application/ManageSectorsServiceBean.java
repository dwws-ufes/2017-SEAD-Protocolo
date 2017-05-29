package br.ufes.inf.nemo.marvin.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.marvin.core.domain.Sector;
import br.ufes.inf.nemo.marvin.core.persistence.SectorDAO;

@Stateless
@PermitAll
public class ManageSectorsServiceBean extends CrudServiceBean<Sector> implements ManageSectorsService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3646745290022025450L;

	@EJB 
	private SectorDAO sectorDAO;
	
	@Override
	public BaseDAO<Sector> getDAO() {
		// TODO Auto-generated method stub
		return sectorDAO;
	}
}
