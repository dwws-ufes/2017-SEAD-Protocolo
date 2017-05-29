package br.ufes.inf.nemo.marvin.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.primefaces.event.ToggleEvent;

import com.google.gson.Gson;

import br.ufes.inf.nemo.jbutler.ejb.controller.JSFController;
import br.ufes.inf.nemo.marvin.core.application.InstallSystemService;
import br.ufes.inf.nemo.marvin.core.domain.User;
import br.ufes.inf.nemo.marvin.core.domain.UserTypes;
import br.ufes.inf.nemo.marvin.core.domain.Endereco;
import br.ufes.inf.nemo.marvin.core.domain.MarvinConfiguration;
import br.ufes.inf.nemo.marvin.core.exceptions.SystemInstallFailedException;
import br.ufes.inf.nemo.marvin.people.domain.Telephone;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Named
@ConversationScoped
public class InstallSystemController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/installSystem/";
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(InstallSystemController.class.getCanonicalName());

	/** The JSF conversation. */
	@Inject
	private Conversation conversation;
		
	/** The "Install System" service. */
	@EJB
	private InstallSystemService installSystemService;

	/** Input: the administrator being registered during the installation. */
	private User admin = new User();

	/** Input: the repeated password for the admininstrator registration. */
	private String repeatPassword;

	/** Input: system configuration information. */
	private MarvinConfiguration config = new MarvinConfiguration();
	
	private Telephone insertedPhone = new Telephone();
	private Set<Telephone> phoneList;
	
	public void findAddress(){
		String URL = "https://viacep.com.br/ws/"+this.admin.getAddress().getCep()+"/json/";
		System.out.println(URL);
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL);
		
		Response response = target.request().get();
		
		String json = response.readEntity(String.class);
		response.close();
		
		Endereco endereco = new Gson().fromJson(json, Endereco.class);
		
		this.admin.getAddress().setDistrict(endereco.getBairro());
		this.admin.getAddress().setCity(endereco.getLocalidade());
		this.admin.getAddress().setState(endereco.getUf());
		this.admin.getAddress().setStreet(endereco.getLogradouro());
		
	}
	
    public Set<Telephone> getPhoneList() {
		return phoneList;
	}

	public void addPhone(){
    	this.phoneList.add(insertedPhone);
    	this.insertedPhone = new Telephone();
    	System.out.println("Adicionado telefone." + phoneList.toString());
    }
	
    public void clearPhone(){
    	this.insertedPhone = new Telephone();
    }
    
    public void onToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
		
	public Telephone getInsertedPhone() {
		return insertedPhone;
	}


	public void setInsertedPhone(Telephone insertedPhone) {
		this.insertedPhone = insertedPhone;
	}
	
	private Map<String,String> phoneTypes = new HashMap<String, String>();
	
	public Map<String, String> getPhoneTypes() {
		return phoneTypes;
	}


	/** Getter for repeatPassword. */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/** Setter for repeatPassword. */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	/** Getter for admin. */
	public User getAdmin() {
		return admin;
	}

	/** Getter for config. */
	public MarvinConfiguration getConfig() {
		return config;
	}

	/**
	 * Analyzes the name that was given to the administrator and, if the short name field is still empty, suggests a
	 * value for it based on the given name.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void suggestUserName() {
		// If the name was filled and the short name is still empty, suggest the first name as short name.
		String name = admin.getName();
		String userName = admin.getUserName();
		if ((name != null) && ((userName == null) || (userName.length() == 0))) {
			String[] partsName = name.split(" ");
			admin.setUserName(partsName[0].toLowerCase()+"."+partsName[partsName.length - 1].toLowerCase());
//			int idx = name.indexOf(" ");
//			admin.setUserName((idx == -1) ? name : name.substring(0, idx).trim());
			logger.log(Level.FINE, "Suggested \"{0}\" as short name for \"{1}\"", new Object[] { admin.getUserName(), name });
		}
		else logger.log(Level.FINEST, "User name not suggested: empty name or user name already filled (name is \"{0}\", short name is \"{1}\")", new Object[] { name, userName });
	}

	/**
	 * Checks if both password fields have the same value.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void ajaxCheckPasswords() {
		checkPasswords();
	}

	/**
	 * Checks if the contents of the password fields match.
	 * 
	 * @return <code>true</code> if the passwords match, <code>false</code> otherwise.
	 */
	private boolean checkPasswords() {
		if (((repeatPassword != null) && (!repeatPassword.equals(admin.getPassword()))) || ((repeatPassword == null) && (admin.getPassword() != null))) {
			logger.log(Level.INFO, "Password and repeated password are not the same");
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_WARN, "installSystem.error.passwordsDontMatch.summary", "installSystem.error.passwordsDontMatch.detail");
			return false;
		}
		return true;
	}

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
	public String registerAdministrator() {
		this.admin.setTelephones(this.phoneList);
		logger.log(Level.FINEST, "Received input data:\n\t- admin.name = {0}\n\t- admin.email = {1}", new Object[] { admin.getName(), admin.getEmail() });

		// Check if passwords don't match. Add an error in that case.
		if (!checkPasswords()) return null;

		// Proceeds to the next view.
		return VIEW_PATH + "config.xhtml?faces-redirect=true";
	}

	/**
	 * Saves the SMTP configuration information and ends the installation process.
	 * 
	 * @return The path to the web page that shows the next step in the installation process.
	 */
	public String saveConfig() {
		logger.log(Level.FINEST, "Previously received data:\n\t- admin.name = {0}\n\t- admin.email = {1}", new Object[] { admin.getName(), admin.getEmail() });
		logger.log(Level.FINEST, "Received input data:\n\t- config.institutionAcronym = {0}", config.getInstitutionAcronym());

		// Installs the system.
		try {
			admin.setUserType(UserTypes.ADMINISTRATOR);
			installSystemService.installSystem(config, admin);
		}
		catch (SystemInstallFailedException e) {
			logger.log(Level.SEVERE, "System installation threw exception", e);
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_FATAL, "installSystem.error.installFailed.summary", "installSystem.error.installFailed.detail");
			return null;
		}

		// Ends the conversation.
		conversation.end();

		// Proceeds to the final view.
		return VIEW_PATH + "done.xhtml?faces-redirect=true";
	}
	
	@PostConstruct
    public void init() {
		phoneList = new HashSet<Telephone>();
        phoneTypes.put("Home Phone", "Home");
        phoneTypes.put("Work Phone", "Work");
        phoneTypes.put("Cell Phone", "Cell");
        phoneTypes.put("Work Ramal", "Ramal");
        phoneTypes.put("Fax", "Fax");
	}

}
