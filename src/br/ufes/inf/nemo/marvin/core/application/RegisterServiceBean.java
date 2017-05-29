package br.ufes.inf.nemo.marvin.core.application;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.ufes.inf.nemo.jbutler.TextUtils;
import br.ufes.inf.nemo.marvin.core.domain.User;
import br.ufes.inf.nemo.marvin.core.exceptions.RegisterFailedException;
import br.ufes.inf.nemo.marvin.core.persistence.UserDAO;


/**
 * TODO: document this type.
 *
 * @author Wagner A. Perin (wagnerperin@gmail.com)
 * @version 1.0
 */
@Stateless
public class RegisterServiceBean implements RegisterService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(RegisterServiceBean.class.getCanonicalName());

	/** The DAO for User objects. */
	@EJB
	private UserDAO userDAO;

	/** Global information about the application. */
	@EJB
	private CoreInformation coreInformation;

	/**
	 * @see br.ufes.inf.nemo.marvin.core.application.InstallSystemService#installSystem(br.ufes.inf.nemo.marvin.core.domain.MarvinConfiguration,
	 *      br.ufes.inf.nemo.marvin.core.domain.User)
	 */
	@Override
	public void register(User user) throws RegisterFailedException {
		logger.log(Level.FINER, "Installing system...");

		try {
			// Encodes the admin's password.
			user.setPassword(TextUtils.produceMd5Hash(user.getPassword()));

			// Register the last update date / creation date.
			Date now = new Date(System.currentTimeMillis());
			user.setLastUpdateDate(now);
			user.setCreationDate(now);
			logger.log(Level.FINE, "Admin's last update date have been set as: {0}", new Object[] { now });

			// Saves the administrator.
			logger.log(Level.FINER, "Persisting admin data...\n\t- User name = {0}\n\t- Last update date = {1}", new Object[] { user.getUserName(), user.getLastUpdateDate() });
			userDAO.save(user);
			logger.log(Level.FINE, "The administrator has been saved: {0} ({1})", new Object[] { user.getName(), user.getEmail() });

			
			// Reloads the bean that holds the configuration and determines if the system is installed.
			logger.log(Level.FINER, "Reloading core information...");
			coreInformation.init();
		}
		catch (NoSuchAlgorithmException e) {
			// Logs and rethrows the exception for the controller to display the error to the user.
			logger.log(Level.SEVERE, "Could not find MD5 algorithm for password encription!", e);
			throw new RegisterFailedException(e);
		}
		catch (Exception e) {
			// Logs and rethrows the exception for the controller to display the error to the user.
			logger.log(Level.SEVERE, "Exception during system installation!", e);
			throw new RegisterFailedException(e);
		}
	}

}
