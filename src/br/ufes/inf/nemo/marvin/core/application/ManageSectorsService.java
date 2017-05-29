package br.ufes.inf.nemo.marvin.core.application;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;

@Local
public interface ManageSectorsService extends CrudService<br.ufes.inf.nemo.marvin.core.domain.Sector> {

}
