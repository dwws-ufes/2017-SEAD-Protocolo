package br.ufes.inf.nemo.marvin.core.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.inf.nemo.marvin.core.application.OpenProtocolService;
import br.ufes.inf.nemo.marvin.core.exceptions.OpenProtocolFailedException;
import br.ufes.inf.nemo.marvin.core.domain.Protocol;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: document this type.
 *
 * @author Wagner de A. Perin (wagnerperin@gmail.com)
 * @version 1.0
 */
@Named
@ConversationScoped
public class OpenProtocolController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/openProtocol/";
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(OpenProtocolController.class.getCanonicalName());

	/** The JSF conversation. */
	@Inject
	private Conversation conversation;
	
	/** The "Install System" service. */
	@EJB
	private OpenProtocolService openProtocolService;

	/** Input: the administrator being registered during the installation. */
	private @Getter @Setter Protocol newProtocol = new Protocol();

	
	/**
	 * Begins the installation process.
	 * 
	 * @return The path to the web page that shows the first step of the installation process.
	 */
	public String begin() {
		logger.log(Level.FINEST, "Beginning conversation. Current conversation transient? -> {0}", new Object[] { conversation.isTransient() });

		// Begins the conversation, dropping any previous conversation, if existing.
		if (!conversation.isTransient()) conversation.end();
			conversation.begin();

		// Go to the first view.
		return VIEW_PATH + "index.xhtml?faces-redirect=true";
	}

	/**
	 * Registers the administrator as one of the steps of system installation and moves to the next step.
	 * 
	 * @return The path to the web page that shows the next step in the installation process.
	 */
	public String openProtocol() {
		SessionController controller =  FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionController}", SessionController.class);
		this.newProtocol.setUser(controller.getCurrentUser());
		try {
			openProtocolService.openProtocol(newProtocol);
		} catch (OpenProtocolFailedException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "openProtocol threw exception", e);
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_FATAL, "openProtocol.error.openProtocolFailed.summary", "openProtocol.error.openProtocolFailed.detail");
			return null;
		}
		
		// Proceeds to the next view.
		return VIEW_PATH + "done.xhtml?faces-redirect=true";
	}
	
	
	public void end(){

		conversation.end();
	}
}
