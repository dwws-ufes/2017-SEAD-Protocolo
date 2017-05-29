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
import br.ufes.inf.nemo.marvin.core.application.RegisterService;
import br.ufes.inf.nemo.marvin.core.domain.User;
import br.ufes.inf.nemo.marvin.core.domain.UserTypes;
import br.ufes.inf.nemo.marvin.core.exceptions.RegisterFailedException;
import br.ufes.inf.nemo.marvin.core.domain.Endereco;
import br.ufes.inf.nemo.marvin.people.domain.Telephone;
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
public class RegisterController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/register/";
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(RegisterController.class.getCanonicalName());

	/** The JSF conversation. */
	@Inject
	private Conversation conversation;
	
	/** The "Install System" service. */
	@EJB
	private RegisterService registerService;

	/** Input: the administrator being registered during the installation. */
	private @Getter @Setter User newUser = new User();

	/** Input: the repeated password for the admininstrator registration. */
	private @Getter @Setter String repeatPassword;
	
	private @Getter @Setter Telephone insertedPhone = new Telephone();
	private @Getter Set<Telephone> phoneList;
	
	public void findAddress(){
		String URL = "https://viacep.com.br/ws/"+this.newUser.getAddress().getCep()+"/json/";
		System.out.println(URL);
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL);
		
		Response response = target.request().get();
		
		String json = response.readEntity(String.class);
		response.close();
		
		Endereco endereco = new Gson().fromJson(json, Endereco.class);
		
		this.newUser.getAddress().setDistrict(endereco.getBairro());
		this.newUser.getAddress().setCity(endereco.getLocalidade());
		this.newUser.getAddress().setState(endereco.getUf());
		this.newUser.getAddress().setStreet(endereco.getLogradouro());
		
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
			
	private @Getter Map<String,String> phoneTypes = new HashMap<String, String>();

	/**
	 * Analyzes the name that was given to the administrator and, if the short name field is still empty, suggests a
	 * value for it based on the given name.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void suggestUserName() {
		// If the name was filled and the short name is still empty, suggest the first name as short name.
		String name = newUser.getName();
		String userName = newUser.getUserName();
		if ((name != null) && ((userName == null) || (userName.length() == 0))) {
			String[] partsName = name.split(" ");
			newUser.setUserName(partsName[0].toLowerCase()+"."+partsName[partsName.length - 1].toLowerCase());
//			int idx = name.indexOf(" ");
//			admin.setUserName((idx == -1) ? name : name.substring(0, idx).trim());
			logger.log(Level.FINE, "Suggested \"{0}\" as short name for \"{1}\"", new Object[] { newUser.getUserName(), name });
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
		if (((repeatPassword != null) && (!repeatPassword.equals(newUser.getPassword()))) || ((repeatPassword == null) && (newUser.getPassword() != null))) {
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
	public String register() {
		this.newUser.setTelephones(this.phoneList);
		this.newUser.setUserType(UserTypes.REGULAR);
		logger.log(Level.FINEST, "Received input data:\n\t- admin.name = {0}\n\t- admin.email = {1}", new Object[] { newUser.getName(), newUser.getEmail() });

		// Check if passwords don't match. Add an error in that case.
		if (!checkPasswords()) return null;
		
		try {
			registerService.register(newUser);
		} catch (RegisterFailedException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "System installation threw exception", e);
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_FATAL, "register.error.registerFailed.summary", "register.error.registerFailed.detail");
			return null;
		}
		
		
		// Proceeds to the next view.
		return VIEW_PATH + "done.xhtml?faces-redirect=true";
	}
	
	public void end(){

		conversation.end();
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
