package br.ufes.inf.nemo.marvin.core.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.application.filters.LikeFilter;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.inf.nemo.marvin.core.application.ManageSectorsService;
import br.ufes.inf.nemo.marvin.core.domain.Sector;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class ManageSectorsController extends CrudController<Sector> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -344208648037803164L;
	
	
	
	@EJB 
	private ManageSectorsService manageSectorsService;
	
	
	private @Getter @Setter List<Sector> sectors;
	private @Getter @Setter Sector sector;
	
	@Override
	protected CrudService<Sector> getCrudService() {
		// TODO Auto-generated method stub
		return manageSectorsService;
	}
	
	public String getSelectedSector(){
		return sector!=null && sector.getName()!=null ? sector.toString():"Classe não escolhida";
	}
	
	@Override
	protected void initFilters() {
		addFilter(new LikeFilter("manageSectors.filter.byName", "name", getI18nMessage("msgsCore", "manageSectors.text.filter.byName")));
	}
	
	@PostConstruct
	public void init(){
		 this.sectors = manageSectorsService.getDAO().retrieveAll();
		 System.out.println(sectors);
	}
	
}
