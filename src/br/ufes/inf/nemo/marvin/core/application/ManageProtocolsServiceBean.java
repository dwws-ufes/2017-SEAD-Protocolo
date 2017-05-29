package br.ufes.inf.nemo.marvin.core.application;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudServiceBean;
import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.marvin.core.domain.Protocol;
import br.ufes.inf.nemo.marvin.core.persistence.ProtocolDAO;

@Stateless
@PermitAll
public class ManageProtocolsServiceBean extends CrudServiceBean<Protocol> implements ManageProtocolsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3401667200279337360L;
	
	@EJB 
	private ProtocolDAO protocolDAO;
	
	@Override
	public BaseDAO<Protocol> getDAO() {
		// TODO Auto-generated method stub
		return protocolDAO;
	}
}
