package br.ufes.inf.nemo.marvin.core.exceptions;

/**
 * Application exception that represents the fact that the system installation process has failed to complete.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
public class RegisterFailedException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Constructor from superclass. */
	public RegisterFailedException(Throwable t) {
		super(t);
	}
}
