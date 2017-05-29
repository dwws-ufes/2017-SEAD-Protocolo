package br.ufes.inf.nemo.marvin.core.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.ufes.inf.nemo.marvin.core.domain.Protocol;
import br.ufes.inf.nemo.marvin.core.exceptions.OpenProtocolFailedException;

/**
 * TODO: document this type.
 *
 * @author Wagner A. Perin (wagnerperin@gmail.com)
 * @version 1.0
 */
@Local
public interface OpenProtocolService extends Serializable {
	/**
	 * TODO: document this method.
	 * 
	 * @param user
	 * @throws RegisterFailedException
	 */
	void openProtocol(Protocol call) throws OpenProtocolFailedException;
}
