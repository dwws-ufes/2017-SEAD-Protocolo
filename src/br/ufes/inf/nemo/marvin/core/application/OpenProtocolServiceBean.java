package br.ufes.inf.nemo.marvin.core.application;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.marvin.core.domain.Protocol;
import br.ufes.inf.nemo.marvin.core.exceptions.OpenProtocolFailedException;
import br.ufes.inf.nemo.marvin.core.persistence.ProtocolDAO;


/**
 * TODO: document this type.
 *
 * @author Wagner A. Perin (wagnerperin@gmail.com)
 * @version 1.0
 */
@Stateless
public class OpenProtocolServiceBean implements OpenProtocolService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(OpenProtocolServiceBean.class.getCanonicalName());

	/** The DAO for User objects. */
	@EJB
	private ProtocolDAO protocolDAO;

	/** Global information about the application. */
	@EJB
	private CoreInformation coreInformation;

	/**
	 * @see br.ufes.inf.nemo.marvin.core.application.InstallSystemService#installSystem(br.ufes.inf.nemo.marvin.core.domain.MarvinConfiguration,
	 *      br.ufes.inf.nemo.marvin.core.domain.User)
	 */
	@Override
	public void openProtocol(Protocol protocol) throws OpenProtocolFailedException {
		logger.log(Level.FINER, "Installing system...");

		try {
			
			// Openprotocol the last update date / creation date.
			Date now = new Date(System.currentTimeMillis());
			protocol.setOpenDate(now);
			protocol.setLastMoviment(now);
			logger.log(Level.FINE, "protocol last moviment date have been set as: {0}", new Object[] { now });

			// Saves the administrator.
			logger.log(Level.FINER, "Persisting protocol data...\n\t- Description = {0}\n\t- Last moviment date = {1}", new Object[] { protocol.getDescription(), protocol.getLastMoviment() });
			protocolDAO.save(protocol);
			logger.log(Level.FINE, "The protocol has been open: {0} ({1})", new Object[] { protocol.getDescription(), protocol.getLastMoviment() });

			
			// Reloads the bean that holds the configuration and determines if the system is installed.
			logger.log(Level.FINER, "Reloading core information...");
			coreInformation.init();
		}
		catch (Exception e) {
			// Logs and rethrows the exception for the controller to display the error to the user.
			logger.log(Level.SEVERE, "Exception during open protocol!", e);
			throw new OpenProtocolFailedException(e);
		}
	}

}
