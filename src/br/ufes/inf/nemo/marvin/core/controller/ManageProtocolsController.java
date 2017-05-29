package br.ufes.inf.nemo.marvin.core.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.LikeFilter;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.marvin.core.application.ManageProtocolsService;
import br.ufes.inf.nemo.marvin.core.domain.Protocol;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class ManageProtocolsController extends CrudController<Protocol> {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4209675979583209980L;


	@EJB 
	private ManageProtocolsService manageProtocolsService;
	
	
	private @Getter @Setter List<Protocol> protocols;
	private @Getter @Setter Protocol protocol;
	
	@Override
	protected CrudService<Protocol> getCrudService() {
		// TODO Auto-generated method stub
		return manageProtocolsService;
	}
	
	
	@Override
	protected void initFilters() {
		addFilter(new LikeFilter("manageProtocols.filter.byName", "name", getI18nMessage("msgsCore", "manageProtocols.text.filter.byName")));
	}
	
	@PostConstruct
	public void init(){
		 this.protocols = manageProtocolsService.getDAO().retrieveAll();
		 System.out.println(protocols);
	}
	
}
