package br.ufes.inf.nemo.marvin.core.controller;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.LikeFilter;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.marvin.core.application.ManageUsersService;
import br.ufes.inf.nemo.marvin.core.domain.User;

@Named
@SessionScoped
public class ManageUsersController extends CrudController<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -344208648037803164L;
	
	@EJB 
	private ManageUsersService manageUsersService;
	
	@Override
	protected CrudService<User> getCrudService() {
		// TODO Auto-generated method stub
		return manageUsersService;
	}

	@Override
	protected void initFilters() {
		addFilter(new LikeFilter("manageUsers.filter.byName", "name", getI18nMessage("msgsCore", "manageUsers.text.filter.byName")));
	}
}
